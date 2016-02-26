function breakout_of_frame() {
  if (top.location != location) {
    top.location.href = document.location.href ;
  }
}

function openPopup(url, name) {
	 var params = 'directories=no';
	 //params += 'width='+width+', height='+height;
	 //params += ', top='+top+', left='+left;
	 params += ', location=no';
	 params += ', menubar=no';
	 params += ', resizable=yes';
	 params += ', scrollbars=yes';
	 params += ', status=no';
	 params += ', toolbar=no';
	 newwin=window.open(url, name, params);
	 var left   = (screen.width  - newwin.width) / 2;
	 var top    = (screen.height - newwin.height) /2;

	 if (window.focus) {
		 newwin.focus();
	 }
	 return false;	

}

function trim(s) {
	if(s && s.replace) {
		var t = s.replace(/^\s+/, '');
		return t.replace(/\s+$/, '');
	} else {
		return s;
	}
}
		
function getObjectByID(objectId) {
	if(document.getElementById) {
		return document.getElementById(objectId);
	} else if (document.all) {
		return document.all[objectId];
	} else if (document.layers) {
		return document.layers[objectId];
	} else {
		return null;
	}
}

function getObjectByIDValue(objectId, defaultValue) {
	var obj = getObjectByID(objectId);
	var val = defaultValue;
	if(obj) {
		if(obj.value != undefined) { 
			val = obj.value;
		} else if(obj.innerHTML != undefined) {
			val = obj.innerHTML;
		}
	}
	return val;
}

function setObjectByIDValue(objectId, value) {
	var obj = getObjectByID(objectId);
	if(obj) {
		if(obj.value != undefined) { 
			obj.value = value;
		} else if(obj.innerHTML != undefined) {
			obj.innerHTML = value;
		}
	}	
}

function setObjectByIDClass(objectId, className) {
	var obj = getObjectByID(objectId);
	if(obj) {
		obj.className = className;
	}
}

function setObjectByIDValueAndClass(objectId, value, className) {
	var obj = getObjectByID(objectId);
	if(obj) {
		if(obj.value != undefined) { 
			obj.value = value;
		} else if(obj.innerHTML != undefined) {
			obj.innerHTML = value;
		}
		if(className) {
			obj.className = className;
		}
	}	
}

function setValueWithSelectedText(srcComboId, destInputId) {
	var txt = getSelectText(srcComboId);
	setObjectByIDValue(destInputId, txt);
}

function getSelectText(objectId) {
	var obj = getObjectByID(objectId);
	var t = '';
	if(obj != undefined) {
		if(!isNaN(obj.selectedIndex)) {
			t = obj.options[obj.selectedIndex].text;
		}
	}
	return t;
}

function toInt(n) {
	if(isNaN(n)) {
		return 0;
	} else {
		return parseInt(n, 10);
	}
}

function toDouble(n){
	if(isNaN(n)){
		return 0
	}
	
	return Number(n)
}

function formatTimeNumber(n) {
	n = '' + n;
	
	if(n == null || n == '' || n == '-' || isNaN(n)) {
		return '00';
	}
	
	if(n.length < 2) {
		return '0' + n;
	}
	
	return n;
}

function showWaitSplash() {
	return showSplash('splash', 'content', '');
}

function showWaitSplashWithMessage(message) {
	return showSplash('splash', 'content', message);
}

function showSplash(splashId, contentId, message) {
	var so = getObjectByID(splashId);
	var co = getObjectByID(contentId);
	if(co && co.style) {
		co.style.opacity = "0.40";
		co.style.filter = "alpha(opacity='40')";
	}
	
	if(so && so.style) {
		so.style.visibility = "visible";
		var sw = screen.width;
		so.style.left = Math.round((sw/2) - 100);
	}
	
	if(message != null && message != '') {
		var emo = getObjectByID('extendedWaitMessage');
		if(emo) {
			emo.innerHTML = message;
		}
	}
	return true;
}

function confirmAndWaitSpash(splashId, formObj, message) {
	if(confirm(message)) {
		return showWaitSplash();
	} else {
		return false;
	}
}


/*
 * Parses the input time to the standard format HH:MM
 * The rules are the following:
 * 4, 4a, 4am, 4:00, 4:00 AM, 4 am, 4: ==> 04:00
 * 18, 6p, 6pm, 18:00, 6:00 PM, 6 pm, 18: ==> 18:00
 * 
 * Regular expression: (\d*):*(\d\d)\s*(\w*)
 */
/* CASE 1: hmm[pm] */
var TIME_REGEXP1 = /^\s*(\d{1})(\d\d){1}\s*([aApP][mM]*)*\s*$/;

/* CASE 2: hhmm[pm] */
var TIME_REGEXP2 = /^\s*(\d\d){1}(\d\d){1}\s*([aApP][mM]*)*\s*$/;

/* CASE 3: h[pm], hh[pm], h:[mm][pm], hh:[mm][pm] */
var TIME_REGEXP3 = /^\s*(\d*)[:.]*(\d\d)*\s*([aApP][mM]*)*\s*$/;

function getRegExpMatch(timeTxt) {
	var m = null;
	m = TIME_REGEXP1.exec(timeTxt);
	if(m) { return m;}
	m = TIME_REGEXP2.exec(timeTxt);
	if(m) { return m;}
	return TIME_REGEXP3.exec(timeTxt);
} 
 
function parseTime(timeTxt) {
	if(!hasValue(timeTxt)) {
		return '';
	} else {
		var m = getRegExpMatch(timeTxt);
		if(!m || m == null || trim(m) == '' || m.length <= 0) {
			return '';
		} else {
			var hs = 0;
			var mins = 0
			var d = null;
			// Hours m[1]
			if(m.length > 0 && m[1]) {
				hs = toInt(m[1]);
			}
			// Minutes m[2]
			if(m.length > 1 && m[2]) {
				mins = toInt(m[2]);
			}
			// AM/PM m[3]
			if(m.length > 2 && m[3]) {
				d = m[3];
				if(d && (d.toLowerCase() == 'p' || d.toLowerCase() == 'pm') && hs >= 0 && hs < 12) {
					hs = hs + 12;
				} else if(d && (d.toLowerCase() == 'a' || d.toLowerCase() == 'am') && hs == 12) {
					hs = 0;
				}
			}
			return formatTimeNumber(hs) + ':' + formatTimeNumber(mins);
		}
	}
}

function timeToDisplay(parsedTime) {
	if(parsedTime == null || trim(parsedTime) == '') {
		return '';
	} else {
		// Time to display will be 5p, 4:30a, etc.
		var hours = toInt(getHours(parsedTime));
		var minutes = getMinutes(parsedTime);
		var am_pm;
		
		if(hours > 12) {
			hours = toInt(hours) - 12;
			am_pm = 'p';	
		} else if(hours == 12) {
			am_pm = 'p';
		} else {
			am_pm = 'a';
		}
		
		if(toInt(minutes) > 0) {
			minutes = ':' + minutes;
		} else {
			minutes = '';
		}
		
		return hours + minutes + am_pm;
	}
}

function updateTime(formElement) {
	formElement.value = timeToDisplay(parseTime(formElement.value));
}

function getHours(time) {
	var i = time.indexOf(':');
	if(i >= 0) {
		return time.substring(0, i);
	} else {
		return time.substring(0, 2);
	}
}

function getMinutes(time) {
	var i = time.indexOf(':');
	if(i >= 0) {
		return time.substring(i + 1, time.length);
	} else {
		return time.substring(2, 4);
	}
}

function integerDivision(numerator, denominator) {
    var remainder = numerator % denominator;
    var quotient = ( numerator - remainder ) / denominator;

    if ( quotient >= 0 )
        quotient = Math.floor( quotient );
    else  // negative
        quotient = Math.ceil( quotient );

	return quotient;
}

function timeInMinutes(time) {
	time = trim(time);
	if(time != null && time != '' && time != '-') {
		return toInt(getHours(time)) * 60 + toInt(getMinutes(time));
	} else {
		return 0;
	}
}

function minutesToTime(minutes) {
	var h = integerDivision(minutes, 60);
	var m = minutes % 60;
	
	return formatTimeNumber(h) + ':' + formatTimeNumber(m);
}

function getObjectValueAsTimeInMinutes(objectId, defaultTime) {
	var v = getObjectByIDValue(objectId, defaultTime);
	if(v) {
		var t = parseTime(v);
		return timeInMinutes(t);
	} else {
		return null;
	}
}

function hasValue(s) {
	return s != null && trim(s) != '';
}
