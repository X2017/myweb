//ListBox.js
//Version: 1.0
//This script is created by Samir Nigam. Do not remove, modify, or hide the author information. keep it intact.
//Mail: nigam.samir@hotmail.com

function getScrollBarWidth(){
 	var __scrollBarWidth = null;
    var scrollBarHelper = window.document.createElement("div");
    if(window.ActiveXObject){ //是IE
    	__scrollBarWidth = {
			horizontal: 6,
			vertical: 6
   		};
    	return __scrollBarWidth;
    }
    //if MSIE: 如此设置的话，scroll bar的最大宽度不能大于100px（通常不会）。else OTHER Browsers:scrollBarHelper.style.cssText = "overflow:scroll;";
    scrollBarHelper.style = "display:none;overflow:scroll;width:320px;height:320px;background-color:#eeeeee;";
    scrollBarHelper.style.cssText = "overflow:scroll;width:100px;height:100px;"; 
    document.body.appendChild(scrollBarHelper);
    if(scrollBarHelper){
        __scrollBarWidth = {
            horizontal: scrollBarHelper.offsetHeight - scrollBarHelper.clientHeight,
            vertical: scrollBarHelper.offsetWidth - scrollBarHelper.clientWidth
        };
    }
    document.body.removeChild(scrollBarHelper);
    return __scrollBarWidth;
}

function ListBox(Arguments){
    //Public property Version.
    this.Version = '1.0';
	
	//Local variables.
    var Ids = 0;
    var EventHandlers = new Array();
	var Base = Arguments.Base ? Arguments.Base : document.documentElement;
	var Size = Arguments.Rows > 5 ? Arguments.Rows : 6;
	var Width = Arguments.Width ? Arguments.Width : 300;
	var NormalItemColor = Arguments.NormalItemColor ? Arguments.NormalItemColor : 'Black';
	var NormalItemBackColor = Arguments.NormalItemBackColor ? Arguments.NormalItemBackColor : '#ffffff';
	var AlternateItemColor = Arguments.AlternateItemColor ? Arguments.AlternateItemColor : 'Black';
	var AlternateItemBackColor = Arguments.AlternateItemBackColor ? Arguments.AlternateItemBackColor : '#ffffff';
	var SelectedItemColor = Arguments.SelectedItemColor ? Arguments.SelectedItemColor : '#ffffff';
	var SelectedIItemBackColor = Arguments.SelectedIItemBackColor ? Arguments.SelectedIItemBackColor : '#F7A928'; //#E6A301
	var HoverItemColor = Arguments.HoverItemColor ? Arguments.HoverItemColor : '#ffffff';
	var HoverItemBackColor = Arguments.HoverItemBackColor ? Arguments.HoverItemBackColor : '#2259D7';
	var HoverBorderdColor = Arguments.HoverBorderdColor ? Arguments.HoverBorderdColor : 'orange';
	var ClickEventHandler = Arguments.ClickEventHandler ? Arguments.ClickEventHandler : function(){};
 
	var lineHeight = 22;
    var ListBoxDiv = document.createElement('div');
    ListBoxDiv.className = "listBoxDivClass";
	ListBoxDiv.style.backgroundColor = '#ffffff';
	ListBoxDiv.style.textAlign = 'left';
	ListBoxDiv.style.verticalAlign = 'top';
	ListBoxDiv.style.cursor = 'default';
	ListBoxDiv.style.borderStyle = 'inset';
//	ListBoxDiv.style.border = '1px solid blue';
	ListBoxDiv.style.overflowX  = 'hidden';
	ListBoxDiv.style.overflowY  = 'scroll';
	ListBoxDiv.style.width = Width + 'px';
	ListBoxDiv.style.height = (Size * (lineHeight+2)) + 'px'; //item高度指定是22px，实际占用了24px
//	alert(ListBoxDiv.style.height);
	
	this.SetRealWidth = function(realWidth){
		Width = realWidth;
	}
	
    this.AddItem = function(_Text, _Value){
        var Item = null;
		var CheckBox = null;        
        var Span = null;
		
        Item = document.createElement('div');
        Item.style.backgroundColor = Ids % 2 == 0 ? NormalItemBackColor : AlternateItemBackColor;
        Item.style.color = Ids % 2 == 0 ? NormalItemColor : AlternateItemColor;
	    Item.style.fontWeight = 'normal';
	    Item.style.fontFamily = 'Verdana';
	    Item.style.fontSize = '10pt';
        Item.style.textAlign = 'left';
        Item.style.verticalAlign = 'middle';
        Item.style.cursor = 'default';
        Item.style.borderTop = Ids % 2 == 0 ? '1px solid ' + NormalItemBackColor : '1px solid ' + AlternateItemBackColor;
        Item.style.borderBottom = Ids % 2 == 0 ? '1px solid ' + NormalItemBackColor : '1px solid ' + AlternateItemBackColor;
        Item.style.overflow = 'hidden';
        Item.style.textOverflow = 'ellipsis';
        Item.style.margin = "0px";
//		Item.style.width = Width + 'px';
		Item.style.height = "22px"; //item高度是22px
//		Item.style.border = '1px solid red';
		Item.style.cellPadding = "0px";
		Item.style.cellSpacing = "0px";
		Item.ItemIndex = Ids;
		Item.onselectstart = function(){
			return false;
		};
		
//		var table = document.createElement('table');
//		table.style.border = '1px solid green';
//		table.style.margin = "0px";
//		table.style.width = (Width-getScrollBarWidth().vertical) + 'px'; //减滚动条
//		table.style.height = "22px";
//		var tr = document.createElement('tr');
//		var td1 = document.createElement('td');
//		td1.style.border = "1px solid red";
//		td1.style.width = "10px";
//		td1.align = "right";
//		CheckBox = document.createElement('input');
//		CheckBox.type = 'checkbox';
//		td1.appendChild(CheckBox);
//		tr.appendChild(td1);
		
		CheckBox = document.createElement('input');
		CheckBox.type = 'checkbox';
		Item.appendChild(CheckBox);
		
//		Span = document.createElement('span');
//		Span.innerHTML = _Text;
//		Span.value = _Value;
//		Span.title = _Text;
//		var td2 = document.createElement('td');
//		td2.style.border = "1px solid red";
//		td2.align = "left";
//		td2.appendChild(Span);
//		table.onclick = function(){
//			var pointX = window.event.clientX;
//			var pointY = window.event.clientY;
//			var topStrPx = this.style.top;
//			var leftStrPx = this.style.left;
//			var top = parseInt(topStrPx);
//			var left = parseInt(leftStrPx);
//			var rect = {left:left+16,top:top,right:this.style.right,bottom:this.style.right};
//			if(pointX > rect.left && pointX < rect.right && pointY >= rect.top && pointY <= rect.bottom){
//				CheckBox.click();
//			}
//	    }
//		tr.appendChild(td2);
//		table.appendChild(tr);
//		Item.appendChild(table);
		
		Span = document.createElement('span');
		Span.style.margin = "0px 0px 0px 0px";
		Span.style.padding = "0px 0px 0px 0px";
		Span.style.spacing = "0px";
//		Span.style.border = '1px solid red';
		Span.style.display = "inline"; //Span.style.display = "table-cell";
		Span.style.verticalAlign = 'top';
		Span.style.width = (Width-24-getScrollBarWidth().vertical) + 'px'; //减前面的复选框图标、减滚动条
		Span.style.height = "22px";
		Span.innerHTML = _Text;
		Span.value = _Value;
		Span.title = _Value;
		Item.onclick = function(){
			var pointX = window.event.clientX;
			var pointY = window.event.clientY;
			pointY += ListBoxDiv.scrollTop; //scrollTop为div窗口的顶端与可见内容的顶端之间的像素
//			alert(pointY);
			var topStrPx = Base.getBoundingClientRect().top + 2; //加2是因为标签嵌套后，有间距的原因
			var leftStrPx = Base.getBoundingClientRect().left + 2; //不能在页面中使用center对齐
//			alert(Base.getBoundingClientRect().top);
			var top = (this.ItemIndex)*lineHeight+parseInt(topStrPx);
			var left = parseInt(leftStrPx);
			var offsetY = this.ItemIndex*2+2; //div在绘制时多占了两像素
			var rect = {left:left+16,top:top,right:left+Width,bottom:top+lineHeight+offsetY};
//			alert(rect.left+'  '+rect.top+'  '+rect.right+'  '+rect.bottom);
			if(pointX > rect.left && pointX <= rect.right && pointY >= rect.top && pointY <= rect.bottom){
				CheckBox.click();
			}
	    }
	    Item.appendChild(Span);
	    
	    ListBoxDiv.appendChild(Item);
		
	    //Register events.
	    WireUpEventHandler(Item, 'mouseover', function(){ OnMouseOver(CheckBox, Item); });
	    WireUpEventHandler(Item, 'mouseout', function(){ OnMouseOut(CheckBox, Item); });
	    WireUpEventHandler(Item, 'selectstart', function(){ return false; });
	    WireUpEventHandler(CheckBox, 'click', function(){ OnClick(CheckBox, Item); });
	    WireUpEventHandler(CheckBox, 'click', function(){ ClickEventHandler(CheckBox, { IsSelected: CheckBox.checked, Text: _Text, Value: _Value, ItemIndex: Item.ItemIndex }); });   
	    
	    Ids++;
	}
	
    //Public method GetItems.
    this.GetItems = function(){
        var Items = new Array();
		var Divs = ListBoxDiv.getElementsByTagName('div');
        for(var n = 0; n < Divs.length; ++n){
			Items.push({IsSelected: Divs[n].childNodes[0].checked, Text: Divs[n].childNodes[1].innerHTML, Value: Divs[n].childNodes[1].value, ItemIndex: Divs[n].ItemIndex});  
        }
        return Items;
    }
	
    //Public method Dispose.
	this.Dispose = function(){
	    while(EventHandlers.length > 0){
	    	DetachEventHandler(EventHandlers.pop());
	    }
	    Base.removeChild(ListBoxDiv);
	}
	
	//Public method Contains.
	this.Contains = function(Index){
		return typeof(Index) == 'number' && ListBoxDiv.childNodes[Index] ? true : false;
	}
	
	//Public method GetItem.
	this.GetItem = function(Index){
	    var Divs = ListBoxDiv.getElementsByTagName('div');
	    return this.Contains(Index) ? { IsSelected: Divs[Index].childNodes[0].checked, Text: Divs[Index].childNodes[1].innerHTML, Value: Divs[Index].childNodes[1].value, ItemIndex: Index} : null;
	}
	
	this.SelectItem = function(Index){
		if(this.Contains(Index)){
			var Divs = ListBoxDiv.getElementsByTagName('div');
			Divs[Index].childNodes[0].checked = true;
			Divs[Index].bgColor = Divs[Index].style.backgroundColor;
			Divs[Index].fColor = Divs[Index].style.color;
			Divs[Index].bColor = Divs[Index].style.borderTopColor;
			Divs[Index].style.backgroundColor = SelectedIItemBackColor;
			Divs[Index].style.color = SelectedItemColor;
			Divs[Index].style.borderTopColor = Divs[Index].style.borderBottomColor = SelectedIItemBackColor;
		}
	}
	this.UnSelectItem = function(Index){
		if(this.Contains(Index)){
			var Divs = ListBoxDiv.getElementsByTagName('div');
			Divs[Index].childNodes[0].checked = false;
			Divs[Index].style.backgroundColor = "#FFFFFF"; //Divs[Index].bgColor
			Divs[Index].style.color = Divs[Index].fColor;
			Divs[Index].style.borderTopColor = Divs[Index].style.borderBottomColor = Divs[Index].bColor;
			Divs[Index].style.fontWeight = 'normal';
		}
	}
	
	this.ScrollToSpecialItem = function(Index){
		if(this.Contains(Index)){
			var onePageDispSize = parseInt(ListBoxDiv.style.height)/(lineHeight+2);
			var Divs = ListBoxDiv.getElementsByTagName('div');
			var realSize = Divs.length;
			if(realSize <= onePageDispSize){ //无垂直滚动条
				return;
			}else{
				ListBoxDiv.scrollTop = 0;
//				alert((Index-onePageDispSize)*(lineHeight+2));
				if(Index+1 > onePageDispSize){ //Index从0开始计数
					ListBoxDiv.scrollTop = (Index+1-onePageDispSize)*(lineHeight+2);
				}
			}
		}
	}
	
	//Public method DeleteItem.
	this.DeleteItem = function(Index){
	    if(!this.Contains(Index))
	    	return false;
	    try{
			ListBoxDiv.removeChild(ListBoxDiv.childNodes[Index]);
	    }catch(err){
			return false;
	    }
	    return true;
	}
	
	//Public method DeleteItems.
	this.DeleteItems = function(){
		var ItemsRemoved = 0;
		for(var n = ListBoxDiv.childNodes.length - 1; n >= 0; --n){
			try{
				ListBoxDiv.removeChild(ListBoxDiv.childNodes[n]);
				ItemsRemoved++;
		    }catch(err){
			    break;
		    }
		}
		return ItemsRemoved;
	}
	
	//Public method GetTotalItems.
	this.GetTotalItems = function(){
	    return ListBoxDiv.childNodes.length;
	}
	
    //Item mouseover event handler.
    var OnMouseOver = function(CheckBox,Item){
        if(CheckBox.checked)
        	return;
        Item.bgColor = Item.style.backgroundColor;
	    Item.fColor = Item.style.color;
	    Item.bColor = Item.style.borderTopColor;
        Item.style.backgroundColor = HoverItemBackColor;
		Item.style.color = HoverItemColor;
		Item.style.borderTopColor = Item.style.borderBottomColor = HoverBorderdColor;
		Item.style.fontWeight = 'bold';
    }
    
    //Item mouseout event handler.
    var OnMouseOut = function(CheckBox,Item){
        if(CheckBox.checked)
        	return;
		Item.style.backgroundColor = Item.bgColor;
	    Item.style.color = Item.fColor;
	    Item.style.borderTopColor = Item.style.borderBottomColor = Item.bColor;
		Item.style.fontWeight = 'normal';
    }
    
    //CheckBox click event handler.
	var OnClick = function(CheckBox,Item){
	    if(CheckBox.checked){
			Item.style.backgroundColor = SelectedIItemBackColor;
			Item.style.color = SelectedItemColor;
			Item.style.borderTopColor = Item.style.borderBottomColor = SelectedIItemBackColor;
        }else{
            Item.style.backgroundColor = HoverItemBackColor;
		    Item.style.color = HoverItemColor;
			Item.style.borderTopColor = Item.style.borderBottomColor = HoverBorderdColor;
        }
	} 
	
    //Private anonymous method to wire up event handlers.
	var WireUpEventHandler = function(Target,Event,Listener){
	    //Register event.
	    if(Target.addEventListener)
			Target.addEventListener(Event,Listener,false);
	    else if(Target.attachEvent)
			Target.attachEvent('on' + Event, Listener);
	    else{
			Event = 'on' + Event;
			Target.Event = Listener;
		}
	    //Collect event information through object literal.
	    var EVENT = {Target: Target, Event: Event, Listener: Listener}
	    EventHandlers.push(EVENT);
	}
	
	//Private anonymous  method to detach event handlers.
	var DetachEventHandler = function(EVENT){
	    if(EVENT.Target.removeEventListener)
			EVENT.Target.removeEventListener(EVENT.Event, EVENT.Listener, false);
	    else if(EVENT.Target.detachEvent)
			EVENT.Target.detachEvent('on' + EVENT.Event, EVENT.Listener);
	    else{
			EVENT.Event = 'on' + EVENT.Event;
			EVENT.Target.EVENT.Event = null;
	    }
	}
	WireUpEventHandler(ListBoxDiv, 'contextmenu', function(){return false;});
    Base.appendChild(ListBoxDiv);
}











