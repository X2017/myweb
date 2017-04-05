package com.nk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 实用工具类
 */
@SuppressWarnings({"rawtypes"})
public class SharpUtil{
	public static int getDayNum(String datetimeStr){
		String[] stringArray = datetimeStr.split("-");
		int year = 2010;
		int month = 1;
		if(stringArray.length > 0)
			year = Integer.parseInt(stringArray[0]);
		if(stringArray.length > 1)
			month = Integer.parseInt(stringArray[1]);
		int[] myMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(2 == month && 0 == (year % 4) && (0 != (year % 100) || 0 == (year % 400))){
			myMonth[1] = 29;
		}
		return myMonth[month-1];
	}
	/**
	 * 随机返回十六进制的字符串
	 * @param len 返回的字符串长度
	 * @return
	 */
	public static String randomHexString(int len)  {  
        try {  
            StringBuffer result = new StringBuffer();  
            for(int i=0;i<len;i++) {  
                result.append(Integer.toHexString(new Random().nextInt(16)));  
            }  
            return result.toString().toUpperCase();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
	
	
	/**
	 * @param datetimeStr
	 * 返回年月字符串
	 */
	public static String getNextMonth(String datetimeStr){
		String[] stringArray = datetimeStr.split("-");
		int year = 2010;
		int month = 1;
		if(stringArray.length > 0)
			year = Integer.parseInt(stringArray[0]);
		if(stringArray.length > 1)
			month = Integer.parseInt(stringArray[1]);
		if(month == 12){
			year ++;
			month = 1;
		}else{
			month ++;
		}
		String reqStr = year+"-"+month+"";
		if(month < 10){
			reqStr = year+"-0"+month+"";
		}
		return reqStr;
	}
	
	private static List<String> getAllMacAddress(){
		List<String> macList = new ArrayList<String>();
		String os = System.getProperty("os.name");
		if(os!=null&&os.startsWith("Windows")){
			try{
				ProcessBuilder pb = new ProcessBuilder("ipconfig","/all");
				Process p = pb.start();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while((line = br.readLine())!=null){
					String reg = "[[0-9][A-F][a-f]][[0-9][A-F][a-f]]-[[0-9][A-F][a-f]][[0-9][A-F][a-f]]-[[0-9][A-F][a-f]][[0-9][A-F][a-f]]-[[0-9][A-F][a-f]][[0-9][A-F][a-f]]-[[0-9][A-F][a-f]][[0-9][A-F][a-f]]-[[0-9][A-F][a-f]][[0-9][A-F][a-f]]";
					String tempStr = "00-00-00-00-00-00";
					int start = 0;
					int i = 0;
					while((i = line.indexOf("-",start))!=-1){
						if(i-2>=0&&i+15<=line.length()){ //mac为17个字符
							tempStr = line.substring(i-2,i+15);
							if("00-00-00-00-00-00".equals(tempStr)){ //过滤隧道mac
								break;
							}
							if(tempStr.matches(reg)){
								macList.add(tempStr);
								start = i+15+1;
								continue;
							}
						}
						start = i+1;
					}
				}
				br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return macList;
	}

	private static String getMachineName(){
		String machineName = null;
		try{
			InetAddress addr = InetAddress.getLocalHost();
			machineName = addr.getHostName().toString(); //获得本机名称
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		return machineName;
	}
	public static List<String> getUseableRegCode(int encodeBT){
		List<String> useableRegCodeList = new ArrayList<String>();
		List<String> macList = getAllMacAddress();
		String machineName = getMachineName(); //以服务形式启动tomcat时，machineName为空；在控制台启动时，能获取到正确的机器名
		if(machineName == null){
			machineName = "default_machine";
		}
		for(String s:macList){
			String encodeStr = "";
			String sourceStr = s + machineName;
			for(int i = 0; i < sourceStr.length(); i ++){
				byte bValue = (byte)(sourceStr.charAt(i) ^ encodeBT);
				encodeStr += String.format("%02X",bValue);
			}
			useableRegCodeList.add(encodeStr);
		}
		return useableRegCodeList;
	}
	
	/**
	 * 返回用户所选树型列表中的对象的第一层id,第二层id及第三层id.只解释三层
	 */
	public static List<Long> parseSelObjString(String selObjStr){
		List<Long> idList = null;
		try{
			idList = new ArrayList<Long>();
			selObjStr = selObjStr.trim();
			int firstIndex = selObjStr.indexOf("_")+1;
			int secondIndex = selObjStr.indexOf("_",firstIndex)+1;
			int thirdIndex = selObjStr.indexOf("_",secondIndex)+1;
			Long firstId = Long.parseLong(selObjStr.substring(firstIndex,secondIndex-1));
			Long secondId = Long.parseLong(selObjStr.substring(secondIndex,thirdIndex-1));
			Long thirdId = Long.parseLong(selObjStr.substring(thirdIndex));
			idList.add(firstId);
			idList.add(secondId);
			idList.add(thirdId);
		}
		catch(Exception e){
			System.out.println("========解析所选对象字符串出错，请检查输入格式!========");
			return null;
		}
		return idList;
	}
	
	public static String formatObjectListToJsonStr(List<String> jsonStrObjList){
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < jsonStrObjList.size(); i ++){
			if(i != jsonStrObjList.size()-1){
				sb.append(jsonStrObjList.get(i)+",");
			}else{
				sb.append(jsonStrObjList.get(i));
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void formatObjectForJsonStrList(List<? extends Object> sourceList,List<String> jsonStrObjList){
		try{
			Reflection reflection = new Reflection();
			int nObjSize = sourceList.size();
			if(nObjSize == 0){
				return;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			NumberFormat numFormat = NumberFormat.getInstance();
			numFormat.setGroupingUsed(false);
			numFormat.setMaximumFractionDigits(3);
			List<Method> srcMethods = reflection.getMethods(sourceList.get(0).getClass());
			List<Field> srcFields = reflection.getFields(sourceList.get(0).getClass());
			boolean bFirst = false;
			for(int i = 0; i < nObjSize; i ++){
				Object object = sourceList.get(i);
				if(!bFirst){
					String columnName = "[";
					for(int j = 0; j < srcFields.size(); j ++){
						String fieldName = srcFields.get(j).getName();
						if(j != srcFields.size()-1){
							columnName += "'" + fieldName + "',";
						}else{
							columnName += "'" + fieldName + "'";;
						}
					}
					columnName += "]";
					jsonStrObjList.add(columnName);
					bFirst = true;
				}
				String itemJsonStr = "[";
				for(int j = 0; j < srcFields.size(); j ++){ //寻找类的字段名
					Field field = srcFields.get(j);
					String fieldName = field.getName();
					String getMethodName = "get" + fieldName.substring(0,1).toUpperCase(); //寻找类的get方法
					getMethodName += fieldName.substring(1);
					boolean bFindMethod = false;
					for(int k = 0; k < srcMethods.size(); k ++){
						if(srcMethods.get(k).getName().equals(getMethodName)){
							Object getValue = srcMethods.get(k).invoke(object);
							String fieldValue = getValue!=null?getValue.toString():"";
							if(field.getType() == Integer.class){
								fieldValue = numFormat.format(getValue);
							}else if(field.getType() == Long.class){
								fieldValue = numFormat.format(getValue);
							}else if(field.getType() == Short.class){
								fieldValue = numFormat.format(getValue);
							}else if(field.getType() == Double.class){
								fieldValue = numFormat.format(getValue);
							}else if(field.getType() == Float.class){
								fieldValue = numFormat.format(getValue);
							}else if(field.getType() == Date.class){
								fieldValue = dateFormat.format(getValue);
							}
							if(j != srcFields.size()-1){
								itemJsonStr += "'" + fieldValue + "',";
							}else{
								itemJsonStr += "'" + fieldValue + "'";
							}
							bFindMethod = true;
						}
					}
					if(!bFindMethod){
						if(j != srcFields.size()-1){
							itemJsonStr += "'',";
						}else{
							itemJsonStr += "''";
						}
					}
				}
				itemJsonStr += "]";
				jsonStrObjList.add(itemJsonStr);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("========格式化Json对象出错!========");
		}
	}
	
	public static void formatPageObjectToPojo(List<? extends Object> sourceList,List<? extends Object> dstList){
		try{
			Reflection reflection = new Reflection();
			if(sourceList.size() == 0 || dstList.size() == 0){
				return;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Field> srcFields = reflection.getFields(sourceList.get(0).getClass());
			List<Method> srcMethods = reflection.getMethods(sourceList.get(0).getClass());
			List<Field> dstFields = reflection.getFields(dstList.get(0).getClass());
			List<Method> dstMethods = reflection.getMethods(dstList.get(0).getClass());
			for(int i = 0; i < dstList.size(); i ++){
				Object srcObject = sourceList.get(i);
				for(int j = 0; j < dstFields.size(); j ++){ //寻找类的字段名
					String fieldName = dstFields.get(j).getName();
					boolean bExist = false;
					for(int k = 0; k < srcFields.size(); k ++){
						if(srcFields.get(k).getName().equals(fieldName)){
							bExist = true;
							break;
						}
					}
					if(bExist == false) //在vo类中不存在此字段,继续查找下一个字段
						continue;
					if(dstFields.get(j).getType() == Boolean.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Boolean.class,"false",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Byte.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Byte.class,"0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Character.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Character.class,"0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Short.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Short.class,"0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Integer.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Integer.class,"0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Long.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Long.class,"0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Double.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Double.class,"0.0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Float.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Float.class,"0.0",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == String.class){
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,String.class,"",dstMethods,dstList.get(i));
					}else if(dstFields.get(j).getType() == Date.class){
						Date date = new Date(0);
						invokeGetVoSetPojoMethod(fieldName,srcFields,srcMethods,srcObject,Date.class,dateFormat.format(date),dstMethods,dstList.get(i));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("========formatPageObjectToPojo出错，请检查类的格式!========");
		}
	}
	private static void invokeGetVoSetPojoMethod(String fieldName,List<Field> fields,List<Method> srcMethods,Object instance,Class cType,String defalutValue,List<Method> dstMethods,Object dstObject){
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			NumberFormat numFormat = NumberFormat.getInstance();
			numFormat.setGroupingUsed(false);
			String getMethodName = "get" + fieldName.substring(0,1).toUpperCase(); //寻找类的get方法
			getMethodName += fieldName.substring(1);
			boolean bFindMethod = false;
			for(int k = 0; k < srcMethods.size(); k ++){
				if(srcMethods.get(k).getName().equals(getMethodName)){
					bFindMethod = true;
					Object getValue = srcMethods.get(k).invoke(instance);
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase();
					setMethodName += fieldName.substring(1);
					if(null == getValue){ //处理为空的情况
						for(int m = 0; m < dstMethods.size(); m ++){
							if(dstMethods.get(m).getName().equals(setMethodName)){
								if(cType == Boolean.class){
									Boolean realValue = "true".equals(defalutValue)?true:false;
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Byte.class){
									Byte realValue = numFormat.parse(defalutValue).byteValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Character.class){
									char realValue = (char)(numFormat.parse(defalutValue).byteValue());
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Short.class){
									Short realValue = numFormat.parse(defalutValue).shortValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Integer.class){
									Integer realValue = numFormat.parse(defalutValue).intValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Long.class){
									Long realValue = numFormat.parse(defalutValue).longValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Double.class){
									Double realValue = numFormat.parse(defalutValue).doubleValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Float.class){
									Float realValue = numFormat.parse(defalutValue).floatValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == String.class){
									dstMethods.get(m).invoke(dstObject,defalutValue);
								}else if(cType == Date.class){
									Date realTypeValue = dateFormat.parse(defalutValue);
									dstMethods.get(m).invoke(dstObject,realTypeValue);
								}
								break;
							}
						}
					}else{ //处理不为空的情况
						String getValueStr = "".equals(getValue.toString())?defalutValue:getValue.toString();
						for(int m = 0; m < dstMethods.size(); m ++){
							if(dstMethods.get(m).getName().equals(setMethodName)){
								if(cType == Boolean.class){
									Boolean realValue = "true".equals(getValueStr)?true:false;
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Byte.class){
									Byte realValue = numFormat.parse(getValueStr).byteValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Character.class){
									char realValue = (char)(numFormat.parse(getValueStr).byteValue());
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Short.class){
									Short realValue = numFormat.parse(getValueStr).shortValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Integer.class){
									Integer realValue = numFormat.parse(getValueStr).intValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Long.class){
									Long realValue = numFormat.parse(getValueStr).longValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Double.class){
									Double realValue = numFormat.parse(getValueStr).doubleValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == Float.class){
									Float realValue = numFormat.parse(getValueStr).floatValue();
									dstMethods.get(m).invoke(dstObject,realValue);
								}else if(cType == String.class){
									dstMethods.get(m).invoke(dstObject,getValueStr);
								}else if(cType == Date.class){
									Date realTypeValue = dateFormat.parse(getValueStr);
									dstMethods.get(m).invoke(dstObject,realTypeValue);
								}
								break;
							}
						}
					}
					break; //跳出方法的查找与调用,继续下一字段的判断与设置
				}
			}
			if(!bFindMethod){
				System.out.println("没有找到字段"+fieldName+"的get方法!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 本方法中sourceList为用户编写的pojo类,dstList为用户编写的vo类;
	 * pojo类中注意get、set方法的返回类型要为封装类,vo类的所有属性类型全为String,名字与pojo类相同
	 * 调用此方法之前,需要在dstList添加所有pojo对应的vo对象
	 * @param sourceList
	 */
	public static void formatObjectForPage(List<? extends Object> sourceList,List<? extends Object> dstList){
		try{
			Reflection reflection = new Reflection();
			int nObjSize = sourceList.size();
			if(nObjSize == 0){
				return;
			}
			List<Field> srcFields = reflection.getFields(sourceList.get(0).getClass());
			List<Method> srcMethods = reflection.getMethods(sourceList.get(0).getClass());
			List<Field> dstFields = reflection.getFields(dstList.get(0).getClass());
			List<Method> dstMethods = reflection.getMethods(dstList.get(0).getClass());
			for(int i = 0; i < nObjSize; i ++){
				Object object = sourceList.get(i);
				for(int j = 0; j < srcFields.size(); j ++){ //寻找类的字段名
					String fieldName = srcFields.get(j).getName();
					boolean bExist = false;
					for(int k = 0; k < dstFields.size(); k ++){
						if(dstFields.get(k).getName().equals(fieldName)){
							bExist = true;
							break;
						}
					}
					if(bExist == false) //在vo类中不存在此字段,继续查找下一个字段
						continue;
					if(srcFields.get(j).getType() == Boolean.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Boolean.class,false,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Byte.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Byte.class,(byte)0,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Character.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Character.class,(char)0,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Short.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Short.class,(short)0,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Integer.class){ //0将自动包装成Integer类型
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Integer.class,0,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Long.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Long.class,0L,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Double.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Double.class,0.0d,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Float.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Float.class,0.0f,dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == String.class){
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,String.class,"",dstMethods,dstList.get(i));
					}else if(srcFields.get(j).getType() == Date.class){
						Date date = new Date(0);
						invokeGetSetMethod(fieldName,srcFields,srcMethods,object,Date.class,date,dstMethods,dstList.get(i));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("========格式化对象出错，请检查类的格式!========");
		}
	}
	
	/**
	 * 此函数处理Integer、Float、Double类，为空时置为0；不为空时浮点型数保留四位小数
	 * @param fieldName		属性名
	 * @param fields		属性集
	 * @param Methods		方法集
	 * @param instance		类的实例
	 * @param cType			类的类型
	 * @param value			此类的新的属性值，当且仅当此属性的值为空时
	 * @param dstMethods	vo类中的方法
	 * @param dstObject		pojo对应的vo对象
	 */
	private static void invokeGetSetMethod(String fieldName,List<Field> fields,List<Method> srcMethods,Object instance,Class cType,Object value,List<Method> dstMethods,Object dstObject){
		try{
			if(cType != value.getClass()){
				System.out.println(cType.getName()+"与"+value.getClass().getName()+"类型不匹配!");
				return;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			NumberFormat numFormat = NumberFormat.getInstance();
			numFormat.setGroupingUsed(false);
			String getMethodName = "get" + fieldName.substring(0,1).toUpperCase(); //寻找类的get方法
			getMethodName += fieldName.substring(1);
			boolean bFindMethod = false;
			for(int k = 0; k < srcMethods.size(); k ++){
				if(srcMethods.get(k).getName().equals(getMethodName)){
					bFindMethod = true;
					Object getValue = srcMethods.get(k).invoke(instance);
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase();
					setMethodName += fieldName.substring(1);
					if(null == getValue){ //处理为空的情况
						for(int m = 0; m < dstMethods.size(); m ++){ //Integer类型的属性值如果为空的话,那么设置为0
							if(dstMethods.get(m).getName().equals(setMethodName)){ //寻找vo类的set方法,参数类型全都是String
								if(value instanceof Boolean){
									String text = value.toString();
									dstMethods.get(m).invoke(dstObject,text);
								}else if((value instanceof Byte) || (value instanceof Character) || (value instanceof Short)){ //设置此属性值为0
									String text = numFormat.format(value);
									dstMethods.get(m).invoke(dstObject,text);
								}else if((value instanceof Integer) || (value instanceof Long)){
									String text = numFormat.format(value);
									dstMethods.get(m).invoke(dstObject,text);
								}else if((value instanceof Double) || (value instanceof Float)){
									numFormat.setMaximumFractionDigits(4);
									String text = numFormat.format(value);
									dstMethods.get(m).invoke(dstObject,text);
								}else if(value instanceof String){
									dstMethods.get(m).invoke(dstObject,value);
								}else if(value instanceof Date){
									String text = dateFormat.format(value);
									dstMethods.get(m).invoke(dstObject,text);
								}
								break;
							}
						}
					}else{ //处理不为空的情况
						for(int m = 0; m < dstMethods.size(); m ++){
							if(dstMethods.get(m).getName().equals(setMethodName)){ //寻找类的set方法
								if(getValue instanceof Boolean){
									String text = getValue.toString();
									dstMethods.get(m).invoke(dstObject,text);
								}else if((getValue instanceof Byte) || (getValue instanceof Character) || (getValue instanceof Short)){ //保留四位小数
									numFormat.setMaximumIntegerDigits(10);
									numFormat.setGroupingUsed(false);
									String text = numFormat.format(getValue);
									dstMethods.get(m).invoke(dstObject,text);
								}else if((getValue instanceof Integer) || (getValue instanceof Long)){
									numFormat.setMaximumIntegerDigits(10);
									numFormat.setGroupingUsed(false);
									String text = numFormat.format(getValue);
									dstMethods.get(m).invoke(dstObject,text);
								}else if((getValue instanceof Double) || (getValue instanceof Float)){
									numFormat.setMinimumFractionDigits(2);
									numFormat.setMaximumFractionDigits(2);
									String text = numFormat.format(getValue);
									dstMethods.get(m).invoke(dstObject,text);
								}else if(getValue instanceof String){
									dstMethods.get(m).invoke(dstObject,getValue);
								}else if(getValue instanceof Date){
									String text = dateFormat.format(getValue);
									dstMethods.get(m).invoke(dstObject,text);
								}
								break;
							}
						}
					}
					break; //跳出方法的查找与调用,继续下一字段的判断与设置
				}
			}
			if(!bFindMethod){
				System.out.println("没有找到字段"+fieldName+"的get方法!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

/**
 * 下面是工具辅助类
 */
@SuppressWarnings({"rawtypes","unchecked"})
class Reflection{
	/**
	 * 得到某个对象的公共属性
	 */
	public Object getProperty(Object owner, String fieldName) throws Exception{
		Class ownerClass = owner.getClass();
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(owner);
		return property;
	}

	/**
	 * 得到某类的静态公共属性
	 */
	public Object getStaticProperty(String className, String fieldName) throws Exception{
		Class ownerClass = Class.forName(className);
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(ownerClass);
		return property;
	}

	/**
	 * 执行某对象方法
	 */
	public Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception{
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length];
		for(int i = 0, j = args.length; i < j; i++){
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	/**
	 * 执行某类的静态方法
	 */
	public Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception{
		Class ownerClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for(int i = 0, j = args.length; i < j; i++){
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(null, args);
	}

	/**
	 * 新建实例
	 */
	public Object newInstance(String className, Object[] args) throws Exception{
		Class newoneClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for(int i = 0, j = args.length; i < j; i++){
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getConstructor(argsClass);
		return cons.newInstance(args);
	}

	/**
	 * 是不是某个类的实例
	 */
	public boolean isInstance(Object obj, Class cls) {
		return cls.isInstance(obj);
	}

	/**
	 * 得到数组中的某个元素
	 */
	public Object getByArray(Object array, int index) {
		return Array.get(array, index);
	}

	private List allGenericFields;

	/**
	 * 查找出clazz的声明属性 以及 父类的声明属性
	 */
	public List<Field> getFields(Class clazz){
		allGenericFields = new ArrayList(); //防止重复调用该方法 出现脏值
		return _getFields(clazz);
	}

	/**
	 * 查找出clazz的声明属性 以及 父类的声明属性
	 */
	private List<Field> _getFields(Class clazz) {
		if(clazz == null) //如果clazz为空则直接返回
			return this.allGenericFields;
		Object parent = clazz.getGenericSuperclass();
		if(parent != null && !((Class) parent).getName().equals("Object")){ //如果有父类并且父类不是Object 则递归调用
			this._getFields((Class) parent);
		}
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null){ //如果clazz存在声明的属性
			for(int i = 0; i < fields.length; i++)
				this.allGenericFields.add(fields[i]);
		}
		return this.allGenericFields; //存在父类则递归调用
	}

	/**
	 * 查找出clazz的 所有public方法 
	 */
	public List<Method> getMethods(Class clazz) {
		if(clazz == null) //如果clazz为空则直接返回
			return null;
		List listMethods = new ArrayList();
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			listMethods.add(methods[i]);
		}
		return listMethods;
	}

	/**
	 * 执行对象方法
	 */
	public Object invokeMethod(Object obj,String methodName) throws IllegalArgumentException,SecurityException,IllegalAccessException,InvocationTargetException,NoSuchMethodException{
		return obj.getClass().getMethod(methodName).invoke(obj);
	}
	
}







