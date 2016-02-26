/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(typeof dojo=="undefined"){
var dj_global=this;
var dj_currentContext=this;
function dj_undef(_1,_2){
return (typeof (_2||dj_currentContext)[_1]=="undefined");
}
if(dj_undef("djConfig",this)){
var djConfig={};
}
if(dj_undef("dojo",this)){
var dojo={};
}
dojo.global=function(){
return dj_currentContext;
};
dojo.locale=djConfig.locale;
dojo.version={major:0,minor:0,patch:0,flag:"dev",revision:Number("$Rev: 6426 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalProp=function(_3,_4,_5){
if((!_4)||(!_3)){
return undefined;
}
if(!dj_undef(_3,_4)){
return _4[_3];
}
return (_5?(_4[_3]={}):undefined);
};
dojo.parseObjPath=function(_6,_7,_8){
var _9=(_7||dojo.global());
var _a=_6.split(".");
var _b=_a.pop();
for(var i=0,l=_a.length;i<l&&_9;i++){
_9=dojo.evalProp(_a[i],_9,_8);
}
return {obj:_9,prop:_b};
};
dojo.evalObjPath=function(_e,_f){
if(typeof _e!="string"){
return dojo.global();
}
if(_e.indexOf(".")==-1){
return dojo.evalProp(_e,dojo.global(),_f);
}
var ref=dojo.parseObjPath(_e,dojo.global(),_f);
if(ref){
return dojo.evalProp(ref.prop,ref.obj,_f);
}
return null;
};
dojo.errorToString=function(_11){
if(!dj_undef("message",_11)){
return _11.message;
}else{
if(!dj_undef("description",_11)){
return _11.description;
}else{
return _11;
}
}
};
dojo.raise=function(_12,_13){
if(_13){
_12=_12+": "+dojo.errorToString(_13);
}
try{
if(djConfig.isDebug){
dojo.hostenv.println("FATAL exception raised: "+_12);
}
}
catch(e){
}
throw _13||Error(_12);
};
dojo.debug=function(){
};
dojo.debugShallow=function(obj){
};
dojo.profile={start:function(){
},end:function(){
},stop:function(){
},dump:function(){
}};
function dj_eval(_15){
return dj_global.eval?dj_global.eval(_15):eval(_15);
}
dojo.unimplemented=function(_16,_17){
var _18="'"+_16+"' not implemented";
if(_17!=null){
_18+=" "+_17;
}
dojo.raise(_18);
};
dojo.deprecated=function(_19,_1a,_1b){
var _1c="DEPRECATED: "+_19;
if(_1a){
_1c+=" "+_1a;
}
if(_1b){
_1c+=" -- will be removed in version: "+_1b;
}
dojo.debug(_1c);
};
dojo.render=(function(){
function vscaffold(_1d,_1e){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_1d};
for(var i=0;i<_1e.length;i++){
tmp[_1e[i]]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _21={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,delayMozLoadingFix:false,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_21;
}else{
for(var _22 in _21){
if(typeof djConfig[_22]=="undefined"){
djConfig[_22]=_21[_22];
}
}
}
return {name_:"(unset)",version_:"(unset)",getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
}};
})();
dojo.hostenv.getBaseScriptUri=function(){
if(djConfig.baseScriptUri.length){
return djConfig.baseScriptUri;
}
var uri=new String(djConfig.libraryScriptUri||djConfig.baseRelativePath);
if(!uri){
dojo.raise("Nothing returned by getLibraryScriptUri(): "+uri);
}
var _25=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
(function(){
var _26={pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_27,_28){
this.modulePrefixes_[_27]={name:_27,value:_28};
},moduleHasPrefix:function(_29){
var mp=this.modulePrefixes_;
return Boolean(mp[_29]&&mp[_29].value);
},getModulePrefix:function(_2b){
if(this.moduleHasPrefix(_2b)){
return this.modulePrefixes_[_2b].value;
}
return _2b;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],unloadListeners:[],loadNotifying:false};
for(var _2c in _26){
dojo.hostenv[_2c]=_26[_2c];
}
})();
dojo.hostenv.loadPath=function(_2d,_2e,cb){
var uri;
if(_2d.charAt(0)=="/"||_2d.match(/^\w+:/)){
uri=_2d;
}else{
uri=this.getBaseScriptUri()+_2d;
}
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+String(djConfig.cacheBust).replace(/\W+/g,"");
}
try{
return !_2e?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_2e,cb);
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(this.loadedUris[uri]){
return true;
}
var _33=this.getText(uri,null,true);
if(!_33){
return false;
}
this.loadedUris[uri]=true;
if(cb){
_33="("+_33+")";
}
var _34=dj_eval(_33);
if(cb){
cb(_34);
}
return true;
};
dojo.hostenv.loadUriAndCheck=function(uri,_36,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return Boolean(ok&&this.findModule(_36,false));
};
dojo.loaded=function(){
};
dojo.unloaded=function(){
};
dojo.hostenv.loaded=function(){
this.loadNotifying=true;
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
this.modulesLoadedListeners=[];
this.loadNotifying=false;
dojo.loaded();
};
dojo.hostenv.unloaded=function(){
var mll=this.unloadListeners;
while(mll.length){
(mll.pop())();
}
dojo.unloaded();
};
dojo.addOnLoad=function(obj,_3d){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dh.modulesLoadedListeners.push(function(){
obj[_3d]();
});
}
}
if(dh.post_load_&&dh.inFlightCount==0&&!dh.loadNotifying){
dh.callLoaded();
}
};
dojo.addOnUnload=function(obj,_40){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.unloadListeners.push(obj);
}else{
if(arguments.length>1){
dh.unloadListeners.push(function(){
obj[_40]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if(this.loadUriStack.length==0&&this.getTextStack.length==0){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
dojo.hostenv.callLoaded();
}
};
dojo.hostenv.callLoaded=function(){
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
};
dojo.hostenv.getModuleSymbols=function(_42){
var _43=_42.split(".");
for(var i=_43.length;i>0;i--){
var _45=_43.slice(0,i).join(".");
if((i==1)&&!this.moduleHasPrefix(_45)){
_43[0]="../"+_43[0];
}else{
var _46=this.getModulePrefix(_45);
if(_46!=_45){
_43.splice(0,i,_46);
break;
}
}
}
return _43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_47,_48,_49){
if(!_47){
return;
}
_49=this._global_omit_module_check||_49;
var _4a=this.findModule(_47,false);
if(_4a){
return _4a;
}
if(dj_undef(_47,this.loading_modules_)){
this.addedToLoadingCount.push(_47);
}
this.loading_modules_[_47]=1;
var _4b=_47.replace(/\./g,"/")+".js";
var _4c=_47.split(".");
var _4d=this.getModuleSymbols(_47);
var _4e=((_4d[0].charAt(0)!="/")&&!_4d[0].match(/^\w+:/));
var _4f=_4d[_4d.length-1];
var ok;
if(_4f=="*"){
_47=_4c.slice(0,-1).join(".");
while(_4d.length){
_4d.pop();
_4d.push(this.pkgFileName);
_4b=_4d.join("/")+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,!_49?_47:null);
if(ok){
break;
}
_4d.pop();
}
}else{
_4b=_4d.join("/")+".js";
_47=_4c.join(".");
var _51=!_49?_47:null;
ok=this.loadPath(_4b,_51);
if(!ok&&!_48){
_4d.pop();
while(_4d.length){
_4b=_4d.join("/")+".js";
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
_4d.pop();
_4b=_4d.join("/")+"/"+this.pkgFileName+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
}
}
if(!ok&&!_49){
dojo.raise("Could not load '"+_47+"'; last tried '"+_4b+"'");
}
}
if(!_49&&!this["isXDomain"]){
_4a=this.findModule(_47,false);
if(!_4a){
dojo.raise("symbol '"+_47+"' is not defined after loading '"+_4b+"'");
}
}
return _4a;
};
dojo.hostenv.startPackage=function(_52){
var _53=String(_52);
var _54=_53;
var _55=_52.split(/\./);
if(_55[_55.length-1]=="*"){
_55.pop();
_54=_55.join(".");
}
var _56=dojo.evalObjPath(_54,true);
this.loaded_modules_[_53]=_56;
this.loaded_modules_[_54]=_56;
return _56;
};
dojo.hostenv.findModule=function(_57,_58){
var lmn=String(_57);
if(this.loaded_modules_[lmn]){
return this.loaded_modules_[lmn];
}
if(_58){
dojo.raise("no loaded module named '"+_57+"'");
}
return null;
};
dojo.kwCompoundRequire=function(_5a){
var _5b=_5a["common"]||[];
var _5c=_5a[dojo.hostenv.name_]?_5b.concat(_5a[dojo.hostenv.name_]||[]):_5b.concat(_5a["default"]||[]);
for(var x=0;x<_5c.length;x++){
var _5e=_5c[x];
if(_5e.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_5e);
}else{
dojo.hostenv.loadModule(_5e);
}
}
};
dojo.require=function(_5f){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireIf=function(_60,_61){
var _62=arguments[0];
if((_62===true)||(_62=="common")||(_62&&dojo.render[_62].capable)){
var _63=[];
for(var i=1;i<arguments.length;i++){
_63.push(arguments[i]);
}
dojo.require.apply(dojo,_63);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.provide=function(_65){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.registerModulePath=function(_66,_67){
return dojo.hostenv.setModulePrefix(_66,_67);
};
dojo.setModulePrefix=function(_68,_69){
dojo.deprecated("dojo.setModulePrefix(\""+_68+"\", \""+_69+"\")","replaced by dojo.registerModulePath","0.5");
return dojo.registerModulePath(_68,_69);
};
dojo.exists=function(obj,_6b){
var p=_6b.split(".");
for(var i=0;i<p.length;i++){
if(!obj[p[i]]){
return false;
}
obj=obj[p[i]];
}
return true;
};
dojo.hostenv.normalizeLocale=function(_6e){
return _6e?_6e.toLowerCase():dojo.locale;
};
dojo.hostenv.searchLocalePath=function(_6f,_70,_71){
_6f=dojo.hostenv.normalizeLocale(_6f);
var _72=_6f.split("-");
var _73=[];
for(var i=_72.length;i>0;i--){
_73.push(_72.slice(0,i).join("-"));
}
_73.push(false);
if(_70){
_73.reverse();
}
for(var j=_73.length-1;j>=0;j--){
var loc=_73[j]||"ROOT";
var _77=_71(loc);
if(_77){
break;
}
}
};
dojo.hostenv.localesGenerated=["ROOT","es-es","es","it-it","pt-br","de","fr-fr","zh-cn","pt","en-us","zh","fr","zh-tw","it","en-gb","xx","de-de","ko-kr","ja-jp","ko","en","ja"];
dojo.hostenv.registerNlsPrefix=function(){
dojo.registerModulePath("nls","nls");
};
dojo.hostenv.preloadLocalizations=function(){
if(dojo.hostenv.localesGenerated){
dojo.hostenv.registerNlsPrefix();
function preload(_78){
_78=dojo.hostenv.normalizeLocale(_78);
dojo.hostenv.searchLocalePath(_78,true,function(loc){
for(var i=0;i<dojo.hostenv.localesGenerated.length;i++){
if(dojo.hostenv.localesGenerated[i]==loc){
dojo["require"]("nls.dojo_"+loc);
return true;
}
}
return false;
});
}
preload();
var _7b=djConfig.extraLocale||[];
for(var i=0;i<_7b.length;i++){
preload(_7b[i]);
}
}
dojo.hostenv.preloadLocalizations=function(){
};
};
dojo.requireLocalization=function(_7d,_7e,_7f){
dojo.hostenv.preloadLocalizations();
var _80=[_7d,"nls",_7e].join(".");
var _81=dojo.hostenv.findModule(_80);
if(_81){
if(djConfig.localizationComplete&&_81._built){
return;
}
var _82=dojo.hostenv.normalizeLocale(_7f).replace("-","_");
var _83=_80+"."+_82;
if(dojo.hostenv.findModule(_83)){
return;
}
}
_81=dojo.hostenv.startPackage(_80);
var _84=dojo.hostenv.getModuleSymbols(_7d);
var _85=_84.concat("nls").join("/");
var _86;
dojo.hostenv.searchLocalePath(_7f,false,function(loc){
var _88=loc.replace("-","_");
var _89=_80+"."+_88;
var _8a=false;
if(!dojo.hostenv.findModule(_89)){
dojo.hostenv.startPackage(_89);
var _8b=[_85];
if(loc!="ROOT"){
_8b.push(loc);
}
_8b.push(_7e);
var _8c=_8b.join("/")+".js";
_8a=dojo.hostenv.loadPath(_8c,null,function(_8d){
var _8e=function(){
};
_8e.prototype=_86;
_81[_88]=new _8e();
for(var j in _8d){
_81[_88][j]=_8d[j];
}
});
}else{
_8a=true;
}
if(_8a&&_81[_88]){
_86=_81[_88];
}else{
_81[_88]=_86;
}
});
};
(function(){
var _90=djConfig.extraLocale;
if(_90){
if(!_90 instanceof Array){
_90=[_90];
}
var req=dojo.requireLocalization;
dojo.requireLocalization=function(m,b,_94){
req(m,b,_94);
if(_94){
return;
}
for(var i=0;i<_90.length;i++){
req(m,b,_90[i]);
}
};
}
})();
}
if(typeof window!="undefined"){
(function(){
if(djConfig.allowQueryConfig){
var _96=document.location.toString();
var _97=_96.split("?",2);
if(_97.length>1){
var _98=_97[1];
var _99=_98.split("&");
for(var x in _99){
var sp=_99[x].split("=");
if((sp[0].length>9)&&(sp[0].substr(0,9)=="djConfig.")){
var opt=sp[0].substr(9);
try{
djConfig[opt]=eval(sp[1]);
}
catch(e){
djConfig[opt]=sp[1];
}
}
}
}
}
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _9d=document.getElementsByTagName("script");
var _9e=/(__package__|dojo|bootstrap1)\.js([\?\.]|$)/i;
for(var i=0;i<_9d.length;i++){
var src=_9d[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_9e);
if(m){
var _a2=src.substring(0,m.index);
if(src.indexOf("bootstrap1")>-1){
_a2+="../";
}
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=_a2;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=_a2;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var drs=dojo.render.svg;
var dua=(drh.UA=navigator.userAgent);
var dav=(drh.AV=navigator.appVersion);
var t=true;
var f=false;
drh.capable=t;
drh.support.builtin=t;
dr.ver=parseFloat(drh.AV);
dr.os.mac=dav.indexOf("Macintosh")>=0;
dr.os.win=dav.indexOf("Windows")>=0;
dr.os.linux=dav.indexOf("X11")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
var _aa=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_aa>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_aa+6,_aa+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
drh.ie70=drh.ie&&dav.indexOf("MSIE 7.0")>=0;
var cm=document["compatMode"];
drh.quirks=(cm=="BackCompat")||(cm=="QuirksMode")||drh.ie55||drh.ie50;
dojo.locale=dojo.locale||(drh.ie?navigator.userLanguage:navigator.language).toLowerCase();
dr.vml.capable=drh.ie;
drs.capable=f;
drs.support.plugin=f;
drs.support.builtin=f;
var _ac=window["document"];
var tdi=_ac["implementation"];
if((tdi)&&(tdi["hasFeature"])&&(tdi.hasFeature("org.w3c.dom.svg","1.0"))){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
if(drh.safari){
var tmp=dua.split("AppleWebKit/")[1];
var ver=parseFloat(tmp.split(" ")[0]);
if(ver>=420){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.render.name=dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
dojo.hostenv._XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _b0=null;
var _b1=null;
try{
_b0=new XMLHttpRequest();
}
catch(e){
}
if(!_b0){
for(var i=0;i<3;++i){
var _b3=dojo.hostenv._XMLHTTP_PROGIDS[i];
try{
_b0=new ActiveXObject(_b3);
}
catch(e){
_b1=e;
}
if(_b0){
dojo.hostenv._XMLHTTP_PROGIDS=[_b3];
break;
}
}
}
if(!_b0){
return dojo.raise("XMLHTTP not available",_b1);
}
return _b0;
};
dojo.hostenv._blockAsync=false;
dojo.hostenv.getText=function(uri,_b5,_b6){
if(!_b5){
this._blockAsync=true;
}
var _b7=this.getXmlhttpObject();
function isDocumentOk(_b8){
var _b9=_b8["status"];
return Boolean((!_b9)||((200<=_b9)&&(300>_b9))||(_b9==304));
}
if(_b5){
var _ba=this,_bb=null,gbl=dojo.global();
var xhr=dojo.evalObjPath("dojo.io.XMLHTTPTransport");
_b7.onreadystatechange=function(){
if(_bb){
gbl.clearTimeout(_bb);
_bb=null;
}
if(_ba._blockAsync||(xhr&&xhr._blockAsync)){
_bb=gbl.setTimeout(function(){
_b7.onreadystatechange.apply(this);
},10);
}else{
if(4==_b7.readyState){
if(isDocumentOk(_b7)){
_b5(_b7.responseText);
}
}
}
};
}
_b7.open("GET",uri,_b5?true:false);
try{
_b7.send(null);
if(_b5){
return null;
}
if(!isDocumentOk(_b7)){
var err=Error("Unable to load "+uri+" status:"+_b7.status);
err.status=_b7.status;
err.responseText=_b7.responseText;
throw err;
}
}
catch(e){
this._blockAsync=false;
if((_b6)&&(!_b5)){
return null;
}else{
throw e;
}
}
this._blockAsync=false;
return _b7.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_bf){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_bf);
}else{
try{
var _c0=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_c0){
_c0=dojo.body();
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_bf));
_c0.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_bf+"</div>");
}
catch(e2){
window.status=_bf;
}
}
}
};
dojo.addOnLoad(function(){
dojo.hostenv._println_safe=true;
while(dojo.hostenv._println_buffer.length>0){
dojo.hostenv.println(dojo.hostenv._println_buffer.shift());
}
});
function dj_addNodeEvtHdlr(_c2,_c3,fp,_c5){
var _c6=_c2["on"+_c3]||function(){
};
_c2["on"+_c3]=function(){
fp.apply(_c2,arguments);
_c6.apply(_c2,arguments);
};
return true;
}
function dj_load_init(e){
var _c8=(e&&e.type)?e.type.toLowerCase():"load";
if(arguments.callee.initialized||(_c8!="domcontentloaded"&&_c8!="load")){
return;
}
arguments.callee.initialized=true;
if(typeof (_timer)!="undefined"){
clearInterval(_timer);
delete _timer;
}
var _c9=function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
};
if(dojo.hostenv.inFlightCount==0){
_c9();
dojo.hostenv.modulesLoaded();
}else{
dojo.addOnLoad(_c9);
}
}
if(document.addEventListener){
if(dojo.render.html.opera||(dojo.render.html.moz&&!djConfig.delayMozLoadingFix)){
document.addEventListener("DOMContentLoaded",dj_load_init,null);
}
window.addEventListener("load",dj_load_init,null);
}
if(dojo.render.html.ie&&dojo.render.os.win){
document.attachEvent("onreadystatechange",function(e){
if(document.readyState=="complete"){
dj_load_init();
}
});
}
if(/(WebKit|khtml)/i.test(navigator.userAgent)){
var _timer=setInterval(function(){
if(/loaded|complete/.test(document.readyState)){
dj_load_init();
}
},10);
}
if(dojo.render.html.ie){
dj_addNodeEvtHdlr(window,"beforeunload",function(){
dojo.hostenv._unloading=true;
window.setTimeout(function(){
dojo.hostenv._unloading=false;
},0);
});
}
dj_addNodeEvtHdlr(window,"unload",function(){
dojo.hostenv.unloaded();
if((!dojo.render.html.ie)||(dojo.render.html.ie&&dojo.hostenv._unloading)){
dojo.hostenv.unloaded();
}
});
dojo.hostenv.makeWidgets=function(){
var _cb=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_cb=_cb.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_cb=_cb.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_cb.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
var _cc=new dojo.xml.Parse();
if(_cb.length>0){
for(var x=0;x<_cb.length;x++){
var _ce=document.getElementById(_cb[x]);
if(!_ce){
continue;
}
var _cf=_cc.parseElement(_ce,null,true);
dojo.widget.getParser().createComponents(_cf);
}
}else{
if(djConfig.parseWidgets){
var _cf=_cc.parseElement(dojo.body(),null,true);
dojo.widget.getParser().createComponents(_cf);
}
}
}
}
};
dojo.addOnLoad(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(dojo.render.html.ie){
document.namespaces.add("v","urn:schemas-microsoft-com:vml");
document.createStyleSheet().addRule("v\\:*","behavior:url(#default#VML)");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
if(!dj_undef("document",this)){
dj_currentDocument=this.document;
}
dojo.doc=function(){
return dj_currentDocument;
};
dojo.body=function(){
return dojo.doc().body||dojo.doc().getElementsByTagName("body")[0];
};
dojo.byId=function(id,doc){
if((id)&&((typeof id=="string")||(id instanceof String))){
if(!doc){
doc=dj_currentDocument;
}
var ele=doc.getElementById(id);
if(ele&&(ele.id!=id)&&doc.all){
ele=null;
eles=doc.all[id];
if(eles){
if(eles.length){
for(var i=0;i<eles.length;i++){
if(eles[i].id==id){
ele=eles[i];
break;
}
}
}else{
ele=eles;
}
}
}
return ele;
}
return id;
};
dojo.setContext=function(_d4,_d5){
dj_currentContext=_d4;
dj_currentDocument=_d5;
};
dojo._fireCallback=function(_d6,_d7,_d8){
if((_d7)&&((typeof _d6=="string")||(_d6 instanceof String))){
_d6=_d7[_d6];
}
return (_d7?_d6.apply(_d7,_d8||[]):_d6());
};
dojo.withGlobal=function(_d9,_da,_db,_dc){
var _dd;
var _de=dj_currentContext;
var _df=dj_currentDocument;
try{
dojo.setContext(_d9,_d9.document);
_dd=dojo._fireCallback(_da,_db,_dc);
}
finally{
dojo.setContext(_de,_df);
}
return _dd;
};
dojo.withDoc=function(_e0,_e1,_e2,_e3){
var _e4;
var _e5=dj_currentDocument;
try{
dj_currentDocument=_e0;
_e4=dojo._fireCallback(_e1,_e2,_e3);
}
finally{
dj_currentDocument=_e5;
}
return _e4;
};
}
(function(){
if(typeof dj_usingBootstrap!="undefined"){
return;
}
var _e6=false;
var _e7=false;
var _e8=false;
if((typeof this["load"]=="function")&&((typeof this["Packages"]=="function")||(typeof this["Packages"]=="object"))){
_e6=true;
}else{
if(typeof this["load"]=="function"){
_e7=true;
}else{
if(window.widget){
_e8=true;
}
}
}
var _e9=[];
if((this["djConfig"])&&((djConfig["isDebug"])||(djConfig["debugAtAllCosts"]))){
_e9.push("debug.js");
}
if((this["djConfig"])&&(djConfig["debugAtAllCosts"])&&(!_e6)&&(!_e8)){
_e9.push("browser_debug.js");
}
var _ea=djConfig["baseScriptUri"];
if((this["djConfig"])&&(djConfig["baseLoaderUri"])){
_ea=djConfig["baseLoaderUri"];
}
for(var x=0;x<_e9.length;x++){
var _ec=_ea+"src/"+_e9[x];
if(_e6||_e7){
load(_ec);
}else{
try{
document.write("<scr"+"ipt type='text/javascript' src='"+_ec+"'></scr"+"ipt>");
}
catch(e){
var _ed=document.createElement("script");
_ed.src=_ec;
document.getElementsByTagName("head")[0].appendChild(_ed);
}
}
}
})();
dojo.provide("dojo.lang.common");
dojo.lang.inherits=function(_ee,_ef){
if(typeof _ef!="function"){
dojo.raise("dojo.inherits: superclass argument ["+_ef+"] must be a function (subclass: ["+_ee+"']");
}
_ee.prototype=new _ef();
_ee.prototype.constructor=_ee;
_ee.superclass=_ef.prototype;
_ee["super"]=_ef.prototype;
};
dojo.lang._mixin=function(obj,_f1){
var _f2={};
for(var x in _f1){
if((typeof _f2[x]=="undefined")||(_f2[x]!=_f1[x])){
obj[x]=_f1[x];
}
}
if(dojo.render.html.ie&&(typeof (_f1["toString"])=="function")&&(_f1["toString"]!=obj["toString"])&&(_f1["toString"]!=_f2["toString"])){
obj.toString=_f1.toString;
}
return obj;
};
dojo.lang.mixin=function(obj,_f5){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(obj,arguments[i]);
}
return obj;
};
dojo.lang.extend=function(_f8,_f9){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(_f8.prototype,arguments[i]);
}
return _f8;
};
dojo.inherits=dojo.lang.inherits;
dojo.mixin=dojo.lang.mixin;
dojo.extend=dojo.lang.extend;
dojo.lang.find=function(_fc,_fd,_fe,_ff){
if(!dojo.lang.isArrayLike(_fc)&&dojo.lang.isArrayLike(_fd)){
dojo.deprecated("dojo.lang.find(value, array)","use dojo.lang.find(array, value) instead","0.5");
var temp=_fc;
_fc=_fd;
_fd=temp;
}
var _101=dojo.lang.isString(_fc);
if(_101){
_fc=_fc.split("");
}
if(_ff){
var step=-1;
var i=_fc.length-1;
var end=-1;
}else{
var step=1;
var i=0;
var end=_fc.length;
}
if(_fe){
while(i!=end){
if(_fc[i]===_fd){
return i;
}
i+=step;
}
}else{
while(i!=end){
if(_fc[i]==_fd){
return i;
}
i+=step;
}
}
return -1;
};
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(_105,_106,_107){
return dojo.lang.find(_105,_106,_107,true);
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(_108,_109){
return dojo.lang.find(_108,_109)>-1;
};
dojo.lang.isObject=function(it){
if(typeof it=="undefined"){
return false;
}
return (typeof it=="object"||it===null||dojo.lang.isArray(it)||dojo.lang.isFunction(it));
};
dojo.lang.isArray=function(it){
return (it&&it instanceof Array||typeof it=="array");
};
dojo.lang.isArrayLike=function(it){
if((!it)||(dojo.lang.isUndefined(it))){
return false;
}
if(dojo.lang.isString(it)){
return false;
}
if(dojo.lang.isFunction(it)){
return false;
}
if(dojo.lang.isArray(it)){
return true;
}
if((it.tagName)&&(it.tagName.toLowerCase()=="form")){
return false;
}
if(dojo.lang.isNumber(it.length)&&isFinite(it.length)){
return true;
}
return false;
};
dojo.lang.isFunction=function(it){
if(!it){
return false;
}
if((typeof (it)=="function")&&(it=="[object NodeList]")){
return false;
}
return (it instanceof Function||typeof it=="function");
};
dojo.lang.isString=function(it){
return (typeof it=="string"||it instanceof String);
};
dojo.lang.isAlien=function(it){
if(!it){
return false;
}
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(it));
};
dojo.lang.isBoolean=function(it){
return (it instanceof Boolean||typeof it=="boolean");
};
dojo.lang.isNumber=function(it){
return (it instanceof Number||typeof it=="number");
};
dojo.lang.isUndefined=function(it){
return ((typeof (it)=="undefined")&&(it==undefined));
};
dojo.provide("dojo.lang.array");
dojo.lang.has=function(obj,name){
try{
return typeof obj[name]!="undefined";
}
catch(e){
return false;
}
};
dojo.lang.isEmpty=function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _117=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_117++;
break;
}
}
return _117==0;
}else{
if(dojo.lang.isArrayLike(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
};
dojo.lang.map=function(arr,obj,_11b){
var _11c=dojo.lang.isString(arr);
if(_11c){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_11b)){
_11b=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_11b){
var _11d=obj;
obj=_11b;
_11b=_11d;
}
}
if(Array.map){
var _11e=Array.map(arr,_11b,obj);
}else{
var _11e=[];
for(var i=0;i<arr.length;++i){
_11e.push(_11b.call(obj,arr[i]));
}
}
if(_11c){
return _11e.join("");
}else{
return _11e;
}
};
dojo.lang.reduce=function(arr,_121,obj,_123){
var _124=_121;
var ob=obj?obj:dj_global;
dojo.lang.map(arr,function(val){
_124=_123.call(ob,_124,val);
});
return _124;
};
dojo.lang.forEach=function(_127,_128,_129){
if(dojo.lang.isString(_127)){
_127=_127.split("");
}
if(Array.forEach){
Array.forEach(_127,_128,_129);
}else{
if(!_129){
_129=dj_global;
}
for(var i=0,l=_127.length;i<l;i++){
_128.call(_129,_127[i],i,_127);
}
}
};
dojo.lang._everyOrSome=function(_12c,arr,_12e,_12f){
if(dojo.lang.isString(arr)){
arr=arr.split("");
}
if(Array.every){
return Array[_12c?"every":"some"](arr,_12e,_12f);
}else{
if(!_12f){
_12f=dj_global;
}
for(var i=0,l=arr.length;i<l;i++){
var _132=_12e.call(_12f,arr[i],i,arr);
if(_12c&&!_132){
return false;
}else{
if((!_12c)&&(_132)){
return true;
}
}
}
return Boolean(_12c);
}
};
dojo.lang.every=function(arr,_134,_135){
return this._everyOrSome(true,arr,_134,_135);
};
dojo.lang.some=function(arr,_137,_138){
return this._everyOrSome(false,arr,_137,_138);
};
dojo.lang.filter=function(arr,_13a,_13b){
var _13c=dojo.lang.isString(arr);
if(_13c){
arr=arr.split("");
}
var _13d;
if(Array.filter){
_13d=Array.filter(arr,_13a,_13b);
}else{
if(!_13b){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_13b=dj_global;
}
_13d=[];
for(var i=0;i<arr.length;i++){
if(_13a.call(_13b,arr[i],i,arr)){
_13d.push(arr[i]);
}
}
}
if(_13c){
return _13d.join("");
}else{
return _13d;
}
};
dojo.lang.unnest=function(){
var out=[];
for(var i=0;i<arguments.length;i++){
if(dojo.lang.isArrayLike(arguments[i])){
var add=dojo.lang.unnest.apply(this,arguments[i]);
out=out.concat(add);
}else{
out.push(arguments[i]);
}
}
return out;
};
dojo.lang.toArray=function(_142,_143){
var _144=[];
for(var i=_143||0;i<_142.length;i++){
_144.push(_142[i]);
}
return _144;
};
dojo.provide("dojo.lang.type");
dojo.lang.whatAmI=function(_146){
dojo.deprecated("dojo.lang.whatAmI","use dojo.lang.getType instead","0.5");
return dojo.lang.getType(_146);
};
dojo.lang.whatAmI.custom={};
dojo.lang.getType=function(_147){
try{
if(dojo.lang.isArray(_147)){
return "array";
}
if(dojo.lang.isFunction(_147)){
return "function";
}
if(dojo.lang.isString(_147)){
return "string";
}
if(dojo.lang.isNumber(_147)){
return "number";
}
if(dojo.lang.isBoolean(_147)){
return "boolean";
}
if(dojo.lang.isAlien(_147)){
return "alien";
}
if(dojo.lang.isUndefined(_147)){
return "undefined";
}
for(var name in dojo.lang.whatAmI.custom){
if(dojo.lang.whatAmI.custom[name](_147)){
return name;
}
}
if(dojo.lang.isObject(_147)){
return "object";
}
}
catch(e){
}
return "unknown";
};
dojo.lang.isNumeric=function(_149){
return (!isNaN(_149)&&isFinite(_149)&&(_149!=null)&&!dojo.lang.isBoolean(_149)&&!dojo.lang.isArray(_149)&&!/^\s*$/.test(_149));
};
dojo.lang.isBuiltIn=function(_14a){
return (dojo.lang.isArray(_14a)||dojo.lang.isFunction(_14a)||dojo.lang.isString(_14a)||dojo.lang.isNumber(_14a)||dojo.lang.isBoolean(_14a)||(_14a==null)||(_14a instanceof Error)||(typeof _14a=="error"));
};
dojo.lang.isPureObject=function(_14b){
return ((_14b!=null)&&dojo.lang.isObject(_14b)&&_14b.constructor==Object);
};
dojo.lang.isOfType=function(_14c,type,_14e){
var _14f=false;
if(_14e){
_14f=_14e["optional"];
}
if(_14f&&((_14c===null)||dojo.lang.isUndefined(_14c))){
return true;
}
if(dojo.lang.isArray(type)){
var _150=type;
for(var i in _150){
var _152=_150[i];
if(dojo.lang.isOfType(_14c,_152)){
return true;
}
}
return false;
}else{
if(dojo.lang.isString(type)){
type=type.toLowerCase();
}
switch(type){
case Array:
case "array":
return dojo.lang.isArray(_14c);
case Function:
case "function":
return dojo.lang.isFunction(_14c);
case String:
case "string":
return dojo.lang.isString(_14c);
case Number:
case "number":
return dojo.lang.isNumber(_14c);
case "numeric":
return dojo.lang.isNumeric(_14c);
case Boolean:
case "boolean":
return dojo.lang.isBoolean(_14c);
case Object:
case "object":
return dojo.lang.isObject(_14c);
case "pureobject":
return dojo.lang.isPureObject(_14c);
case "builtin":
return dojo.lang.isBuiltIn(_14c);
case "alien":
return dojo.lang.isAlien(_14c);
case "undefined":
return dojo.lang.isUndefined(_14c);
case null:
case "null":
return (_14c===null);
case "optional":
dojo.deprecated("dojo.lang.isOfType(value, [type, \"optional\"])","use dojo.lang.isOfType(value, type, {optional: true} ) instead","0.5");
return ((_14c===null)||dojo.lang.isUndefined(_14c));
default:
if(dojo.lang.isFunction(type)){
return (_14c instanceof type);
}else{
dojo.raise("dojo.lang.isOfType() was passed an invalid type");
}
}
}
dojo.raise("If we get here, it means a bug was introduced above.");
};
dojo.lang.getObject=function(str){
var _154=str.split("."),i=0,obj=dj_global;
do{
obj=obj[_154[i++]];
}while(i<_154.length&&obj);
return (obj!=dj_global)?obj:null;
};
dojo.lang.doesObjectExist=function(str){
var _158=str.split("."),i=0,obj=dj_global;
do{
obj=obj[_158[i++]];
}while(i<_158.length&&obj);
return (obj&&obj!=dj_global);
};
dojo.provide("dojo.lang.assert");
dojo.lang.assert=function(_15b,_15c){
if(!_15b){
var _15d="An assert statement failed.\n"+"The method dojo.lang.assert() was called with a 'false' value.\n";
if(_15c){
_15d+="Here's the assert message:\n"+_15c+"\n";
}
throw new Error(_15d);
}
};
dojo.lang.assertType=function(_15e,type,_160){
if(dojo.lang.isString(_160)){
dojo.deprecated("dojo.lang.assertType(value, type, \"message\")","use dojo.lang.assertType(value, type) instead","0.5");
}
if(!dojo.lang.isOfType(_15e,type,_160)){
if(!dojo.lang.assertType._errorMessage){
dojo.lang.assertType._errorMessage="Type mismatch: dojo.lang.assertType() failed.";
}
dojo.lang.assert(false,dojo.lang.assertType._errorMessage);
}
};
dojo.lang.assertValidKeywords=function(_161,_162,_163){
var key;
if(!_163){
if(!dojo.lang.assertValidKeywords._errorMessage){
dojo.lang.assertValidKeywords._errorMessage="In dojo.lang.assertValidKeywords(), found invalid keyword:";
}
_163=dojo.lang.assertValidKeywords._errorMessage;
}
if(dojo.lang.isArray(_162)){
for(key in _161){
if(!dojo.lang.inArray(_162,key)){
dojo.lang.assert(false,_163+" "+key);
}
}
}else{
for(key in _161){
if(!(key in _162)){
dojo.lang.assert(false,_163+" "+key);
}
}
}
};
dojo.provide("dojo.lang.func");
dojo.lang.hitch=function(_165,_166){
var fcn=(dojo.lang.isString(_166)?_165[_166]:_166)||function(){
};
return function(){
return fcn.apply(_165,arguments);
};
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_168,_169,_16a){
var nso=(_169||dojo.lang.anon);
if((_16a)||((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true))){
for(var x in nso){
try{
if(nso[x]===_168){
return x;
}
}
catch(e){
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_168;
return ret;
};
dojo.lang.forward=function(_16e){
return function(){
return this[_16e].apply(this,arguments);
};
};
dojo.lang.curry=function(ns,func){
var _171=[];
ns=ns||dj_global;
if(dojo.lang.isString(func)){
func=ns[func];
}
for(var x=2;x<arguments.length;x++){
_171.push(arguments[x]);
}
var _173=(func["__preJoinArity"]||func.length)-_171.length;
function gather(_174,_175,_176){
var _177=_176;
var _178=_175.slice(0);
for(var x=0;x<_174.length;x++){
_178.push(_174[x]);
}
_176=_176-_174.length;
if(_176<=0){
var res=func.apply(ns,_178);
_176=_177;
return res;
}else{
return function(){
return gather(arguments,_178,_176);
};
}
}
return gather([],_171,_173);
};
dojo.lang.curryArguments=function(ns,func,args,_17e){
var _17f=[];
var x=_17e||0;
for(x=_17e;x<args.length;x++){
_17f.push(args[x]);
}
return dojo.lang.curry.apply(dojo.lang,[ns,func].concat(_17f));
};
dojo.lang.tryThese=function(){
for(var x=0;x<arguments.length;x++){
try{
if(typeof arguments[x]=="function"){
var ret=(arguments[x]());
if(ret){
return ret;
}
}
}
catch(e){
dojo.debug(e);
}
}
};
dojo.lang.delayThese=function(farr,cb,_185,_186){
if(!farr.length){
if(typeof _186=="function"){
_186();
}
return;
}
if((typeof _185=="undefined")&&(typeof cb=="number")){
_185=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_185){
_185=0;
}
}
}
setTimeout(function(){
(farr.shift())();
cb();
dojo.lang.delayThese(farr,cb,_185,_186);
},_185);
};
dojo.provide("dojo.lang.extras");
dojo.lang.setTimeout=function(func,_188){
var _189=window,_18a=2;
if(!dojo.lang.isFunction(func)){
_189=func;
func=_188;
_188=arguments[2];
_18a++;
}
if(dojo.lang.isString(func)){
func=_189[func];
}
var args=[];
for(var i=_18a;i<arguments.length;i++){
args.push(arguments[i]);
}
return dojo.global().setTimeout(function(){
func.apply(_189,args);
},_188);
};
dojo.lang.clearTimeout=function(_18d){
dojo.global().clearTimeout(_18d);
};
dojo.lang.getNameInObj=function(ns,item){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===item){
return new String(x);
}
}
return null;
};
dojo.lang.shallowCopy=function(obj,deep){
var i,ret;
if(obj===null){
return null;
}
if(dojo.lang.isObject(obj)){
ret=new obj.constructor();
for(i in obj){
if(dojo.lang.isUndefined(ret[i])){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}
}else{
if(dojo.lang.isArray(obj)){
ret=[];
for(i=0;i<obj.length;i++){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}else{
ret=obj;
}
}
return ret;
};
dojo.lang.firstValued=function(){
for(var i=0;i<arguments.length;i++){
if(typeof arguments[i]!="undefined"){
return arguments[i];
}
}
return undefined;
};
dojo.lang.getObjPathValue=function(_196,_197,_198){
with(dojo.parseObjPath(_196,_197,_198)){
return dojo.evalProp(prop,obj,_198);
}
};
dojo.lang.setObjPathValue=function(_199,_19a,_19b,_19c){
if(arguments.length<4){
_19c=true;
}
with(dojo.parseObjPath(_199,_19b,_19c)){
if(obj&&(_19c||(prop in obj))){
obj[prop]=_19a;
}
}
};
dojo.provide("dojo.AdapterRegistry");
dojo.AdapterRegistry=function(_19d){
this.pairs=[];
this.returnWrappers=_19d||false;
};
dojo.lang.extend(dojo.AdapterRegistry,{register:function(name,_19f,wrap,_1a1,_1a2){
var type=(_1a2)?"unshift":"push";
this.pairs[type]([name,_19f,wrap,_1a1]);
},match:function(){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[1].apply(this,arguments)){
if((pair[3])||(this.returnWrappers)){
return pair[2];
}else{
return pair[2].apply(this,arguments);
}
}
}
throw new Error("No match found");
},unregister:function(name){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[0]==name){
this.pairs.splice(i,1);
return true;
}
}
return false;
}});
dojo.provide("dojo.string.common");
dojo.string.trim=function(str,wh){
if(!str.replace){
return str;
}
if(!str.length){
return str;
}
var re=(wh>0)?(/^\s+/):(wh<0)?(/\s+$/):(/^\s+|\s+$/g);
return str.replace(re,"");
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.repeat=function(str,_1af,_1b0){
var out="";
for(var i=0;i<_1af;i++){
out+=str;
if(_1b0&&i<_1af-1){
out+=_1b0;
}
}
return out;
};
dojo.string.pad=function(str,len,c,dir){
var out=String(str);
if(!c){
c="0";
}
if(!dir){
dir=1;
}
while(out.length<len){
if(dir>0){
out=c+out;
}else{
out+=c;
}
}
return out;
};
dojo.string.padLeft=function(str,len,c){
return dojo.string.pad(str,len,c,1);
};
dojo.string.padRight=function(str,len,c){
return dojo.string.pad(str,len,c,-1);
};
dojo.provide("dojo.string.extras");
dojo.string.substituteParams=function(_1be,hash){
var map=(typeof hash=="object")?hash:dojo.lang.toArray(arguments,1);
return _1be.replace(/\%\{(\w+)\}/g,function(_1c1,key){
if(typeof (map[key])!="undefined"&&map[key]!=null){
return map[key];
}
dojo.raise("Substitution not found: "+key);
});
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _1c4=str.split(" ");
for(var i=0;i<_1c4.length;i++){
_1c4[i]=_1c4[i].charAt(0).toUpperCase()+_1c4[i].substring(1);
}
return _1c4.join(" ");
};
dojo.string.isBlank=function(str){
if(!dojo.lang.isString(str)){
return true;
}
return (dojo.string.trim(str).length==0);
};
dojo.string.encodeAscii=function(str){
if(!dojo.lang.isString(str)){
return str;
}
var ret="";
var _1c9=escape(str);
var _1ca,re=/%u([0-9A-F]{4})/i;
while((_1ca=_1c9.match(re))){
var num=Number("0x"+_1ca[1]);
var _1cd=escape("&#"+num+";");
ret+=_1c9.substring(0,_1ca.index)+_1cd;
_1c9=_1c9.substring(_1ca.index+_1ca[0].length);
}
ret+=_1c9.replace(/\+/g,"%2B");
return ret;
};
dojo.string.escape=function(type,str){
var args=dojo.lang.toArray(arguments,1);
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml.apply(this,args);
case "sql":
return dojo.string.escapeSql.apply(this,args);
case "regexp":
case "regex":
return dojo.string.escapeRegExp.apply(this,args);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript.apply(this,args);
case "ascii":
return dojo.string.encodeAscii.apply(this,args);
default:
return str;
}
};
dojo.string.escapeXml=function(str,_1d2){
str=str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;");
if(!_1d2){
str=str.replace(/'/gm,"&#39;");
}
return str;
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}
return str.substring(0,len).replace(/\.+$/,"")+"...";
};
dojo.string.endsWith=function(str,end,_1db){
if(_1db){
str=str.toLowerCase();
end=end.toLowerCase();
}
if((str.length-end.length)<0){
return false;
}
return str.lastIndexOf(end)==str.length-end.length;
};
dojo.string.endsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.endsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.startsWith=function(str,_1df,_1e0){
if(_1e0){
str=str.toLowerCase();
_1df=_1df.toLowerCase();
}
return str.indexOf(_1df)==0;
};
dojo.string.startsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.startsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.has=function(str){
for(var i=1;i<arguments.length;i++){
if(str.indexOf(arguments[i])>-1){
return true;
}
}
return false;
};
dojo.string.normalizeNewlines=function(text,_1e6){
if(_1e6=="\n"){
text=text.replace(/\r\n/g,"\n");
text=text.replace(/\r/g,"\n");
}else{
if(_1e6=="\r"){
text=text.replace(/\r\n/g,"\r");
text=text.replace(/\n/g,"\r");
}else{
text=text.replace(/([^\r])\n/g,"$1\r\n").replace(/\r([^\n])/g,"\r\n$1");
}
}
return text;
};
dojo.string.splitEscaped=function(str,_1e8){
var _1e9=[];
for(var i=0,_1eb=0;i<str.length;i++){
if(str.charAt(i)=="\\"){
i++;
continue;
}
if(str.charAt(i)==_1e8){
_1e9.push(str.substring(_1eb,i));
_1eb=i+1;
}
}
_1e9.push(str.substr(_1eb));
return _1e9;
};
dojo.provide("dojo.lang.repr");
dojo.lang.reprRegistry=new dojo.AdapterRegistry();
dojo.lang.registerRepr=function(name,_1ed,wrap,_1ef){
dojo.lang.reprRegistry.register(name,_1ed,wrap,_1ef);
};
dojo.lang.repr=function(obj){
if(typeof (obj)=="undefined"){
return "undefined";
}else{
if(obj===null){
return "null";
}
}
try{
if(typeof (obj["__repr__"])=="function"){
return obj["__repr__"]();
}else{
if((typeof (obj["repr"])=="function")&&(obj.repr!=arguments.callee)){
return obj["repr"]();
}
}
return dojo.lang.reprRegistry.match(obj);
}
catch(e){
if(typeof (obj.NAME)=="string"&&(obj.toString==Function.prototype.toString||obj.toString==Object.prototype.toString)){
return obj.NAME;
}
}
if(typeof (obj)=="function"){
obj=(obj+"").replace(/^\s+/,"");
var idx=obj.indexOf("{");
if(idx!=-1){
obj=obj.substr(0,idx)+"{...}";
}
}
return obj+"";
};
dojo.lang.reprArrayLike=function(arr){
try{
var na=dojo.lang.map(arr,dojo.lang.repr);
return "["+na.join(", ")+"]";
}
catch(e){
}
};
(function(){
var m=dojo.lang;
m.registerRepr("arrayLike",m.isArrayLike,m.reprArrayLike);
m.registerRepr("string",m.isString,m.reprString);
m.registerRepr("numbers",m.isNumber,m.reprNumber);
m.registerRepr("boolean",m.isBoolean,m.reprNumber);
})();
dojo.provide("dojo.lang.declare");
dojo.lang.declare=function(_1f5,_1f6,init,_1f8){
if((dojo.lang.isFunction(_1f8))||((!_1f8)&&(!dojo.lang.isFunction(init)))){
var temp=_1f8;
_1f8=init;
init=temp;
}
var _1fa=[];
if(dojo.lang.isArray(_1f6)){
_1fa=_1f6;
_1f6=_1fa.shift();
}
if(!init){
init=dojo.evalObjPath(_1f5,false);
if((init)&&(!dojo.lang.isFunction(init))){
init=null;
}
}
var ctor=dojo.lang.declare._makeConstructor();
var scp=(_1f6?_1f6.prototype:null);
if(scp){
scp.prototyping=true;
ctor.prototype=new _1f6();
scp.prototyping=false;
}
ctor.superclass=scp;
ctor.mixins=_1fa;
for(var i=0,l=_1fa.length;i<l;i++){
dojo.lang.extend(ctor,_1fa[i].prototype);
}
ctor.prototype.initializer=null;
ctor.prototype.declaredClass=_1f5;
if(dojo.lang.isArray(_1f8)){
dojo.lang.extend.apply(dojo.lang,[ctor].concat(_1f8));
}else{
dojo.lang.extend(ctor,(_1f8)||{});
}
dojo.lang.extend(ctor,dojo.lang.declare._common);
ctor.prototype.constructor=ctor;
ctor.prototype.initializer=(ctor.prototype.initializer)||(init)||(function(){
});
dojo.lang.setObjPathValue(_1f5,ctor,null,true);
return ctor;
};
dojo.lang.declare._makeConstructor=function(){
return function(){
var self=this._getPropContext();
var s=self.constructor.superclass;
if((s)&&(s.constructor)){
if(s.constructor==arguments.callee){
this._inherited("constructor",arguments);
}else{
this._contextMethod(s,"constructor",arguments);
}
}
var ms=(self.constructor.mixins)||([]);
for(var i=0,m;(m=ms[i]);i++){
(((m.prototype)&&(m.prototype.initializer))||(m)).apply(this,arguments);
}
if((!this.prototyping)&&(self.initializer)){
self.initializer.apply(this,arguments);
}
};
};
dojo.lang.declare._common={_getPropContext:function(){
return (this.___proto||this);
},_contextMethod:function(_204,_205,args){
var _207,_208=this.___proto;
this.___proto=_204;
try{
_207=_204[_205].apply(this,(args||[]));
}
catch(e){
throw e;
}
finally{
this.___proto=_208;
}
return _207;
},_inherited:function(prop,args){
var p=this._getPropContext();
do{
if((!p.constructor)||(!p.constructor.superclass)){
return;
}
p=p.constructor.superclass;
}while(!(prop in p));
return (dojo.lang.isFunction(p[prop])?this._contextMethod(p,prop,args):p[prop]);
}};
dojo.declare=dojo.lang.declare;
dojo.provide("dojo.lang.*");
dojo.provide("dojo.dom");
dojo.dom.ELEMENT_NODE=1;
dojo.dom.ATTRIBUTE_NODE=2;
dojo.dom.TEXT_NODE=3;
dojo.dom.CDATA_SECTION_NODE=4;
dojo.dom.ENTITY_REFERENCE_NODE=5;
dojo.dom.ENTITY_NODE=6;
dojo.dom.PROCESSING_INSTRUCTION_NODE=7;
dojo.dom.COMMENT_NODE=8;
dojo.dom.DOCUMENT_NODE=9;
dojo.dom.DOCUMENT_TYPE_NODE=10;
dojo.dom.DOCUMENT_FRAGMENT_NODE=11;
dojo.dom.NOTATION_NODE=12;
dojo.dom.dojoml="http://www.dojotoolkit.org/2004/dojoml";
dojo.dom.xmlns={svg:"http://www.w3.org/2000/svg",smil:"http://www.w3.org/2001/SMIL20/",mml:"http://www.w3.org/1998/Math/MathML",cml:"http://www.xml-cml.org",xlink:"http://www.w3.org/1999/xlink",xhtml:"http://www.w3.org/1999/xhtml",xul:"http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul",xbl:"http://www.mozilla.org/xbl",fo:"http://www.w3.org/1999/XSL/Format",xsl:"http://www.w3.org/1999/XSL/Transform",xslt:"http://www.w3.org/1999/XSL/Transform",xi:"http://www.w3.org/2001/XInclude",xforms:"http://www.w3.org/2002/01/xforms",saxon:"http://icl.com/saxon",xalan:"http://xml.apache.org/xslt",xsd:"http://www.w3.org/2001/XMLSchema",dt:"http://www.w3.org/2001/XMLSchema-datatypes",xsi:"http://www.w3.org/2001/XMLSchema-instance",rdf:"http://www.w3.org/1999/02/22-rdf-syntax-ns#",rdfs:"http://www.w3.org/2000/01/rdf-schema#",dc:"http://purl.org/dc/elements/1.1/",dcq:"http://purl.org/dc/qualifiers/1.0","soap-env":"http://schemas.xmlsoap.org/soap/envelope/",wsdl:"http://schemas.xmlsoap.org/wsdl/",AdobeExtensions:"http://ns.adobe.com/AdobeSVGViewerExtensions/3.0/"};
dojo.dom.isNode=function(wh){
if(typeof Element=="function"){
try{
return wh instanceof Element;
}
catch(E){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.dom.getUniqueId=function(){
var _20d=dojo.doc();
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(_20d.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_20f,_210){
var node=_20f.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_210&&node&&node.tagName&&node.tagName.toLowerCase()!=_210.toLowerCase()){
node=dojo.dom.nextElement(node,_210);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_212,_213){
var node=_212.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_213&&node&&node.tagName&&node.tagName.toLowerCase()!=_213.toLowerCase()){
node=dojo.dom.prevElement(node,_213);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_216){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_216&&_216.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_216);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_218){
if(!node){
return null;
}
if(_218){
_218=_218.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_218&&_218.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_218);
}
return node;
};
dojo.dom.moveChildren=function(_219,_21a,trim){
var _21c=0;
if(trim){
while(_219.hasChildNodes()&&_219.firstChild.nodeType==dojo.dom.TEXT_NODE){
_219.removeChild(_219.firstChild);
}
while(_219.hasChildNodes()&&_219.lastChild.nodeType==dojo.dom.TEXT_NODE){
_219.removeChild(_219.lastChild);
}
}
while(_219.hasChildNodes()){
_21a.appendChild(_219.firstChild);
_21c++;
}
return _21c;
};
dojo.dom.copyChildren=function(_21d,_21e,trim){
var _220=_21d.cloneNode(true);
return this.moveChildren(_220,_21e,trim);
};
dojo.dom.removeChildren=function(node){
var _222=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _222;
};
dojo.dom.replaceChildren=function(node,_224){
dojo.dom.removeChildren(node);
node.appendChild(_224);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_227,_228){
var _229=[];
var _22a=(_227&&(_227 instanceof Function||typeof _227=="function"));
while(node){
if(!_22a||_227(node)){
_229.push(node);
}
if(_228&&_229.length>0){
return _229[0];
}
node=node.parentNode;
}
if(_228){
return null;
}
return _229;
};
dojo.dom.getAncestorsByTag=function(node,tag,_22d){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_22d);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_232,_233){
if(_233&&node){
node=node.parentNode;
}
while(node){
if(node==_232){
return true;
}
node=node.parentNode;
}
return false;
};
dojo.dom.innerXML=function(node){
if(node.innerXML){
return node.innerXML;
}else{
if(node.xml){
return node.xml;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
}
};
dojo.dom.createDocument=function(){
var doc=null;
var _236=dojo.doc();
if(!dj_undef("ActiveXObject")){
var _237=["MSXML2","Microsoft","MSXML","MSXML3"];
for(var i=0;i<_237.length;i++){
try{
doc=new ActiveXObject(_237[i]+".XMLDOM");
}
catch(e){
}
if(doc){
break;
}
}
}else{
if((_236.implementation)&&(_236.implementation.createDocument)){
doc=_236.implementation.createDocument("","",null);
}
}
return doc;
};
dojo.dom.createDocumentFromText=function(str,_23a){
if(!_23a){
_23a="text/xml";
}
if(!dj_undef("DOMParser")){
var _23b=new DOMParser();
return _23b.parseFromString(str,_23a);
}else{
if(!dj_undef("ActiveXObject")){
var _23c=dojo.dom.createDocument();
if(_23c){
_23c.async=false;
_23c.loadXML(str);
return _23c;
}else{
dojo.debug("toXml didn't work?");
}
}else{
var _23d=dojo.doc();
if(_23d.createElement){
var tmp=_23d.createElement("xml");
tmp.innerHTML=str;
if(_23d.implementation&&_23d.implementation.createDocument){
var _23f=_23d.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_23f.importNode(tmp.childNodes.item(i),true);
}
return _23f;
}
return ((tmp.document)&&(tmp.document.firstChild?tmp.document.firstChild:tmp));
}
}
}
return null;
};
dojo.dom.prependChild=function(node,_242){
if(_242.firstChild){
_242.insertBefore(node,_242.firstChild);
}else{
_242.appendChild(node);
}
return true;
};
dojo.dom.insertBefore=function(node,ref,_245){
if(_245!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _246=ref.parentNode;
_246.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_249){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_249!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_249);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_24d){
if((!node)||(!ref)||(!_24d)){
return false;
}
switch(_24d.toLowerCase()){
case "before":
return dojo.dom.insertBefore(node,ref);
case "after":
return dojo.dom.insertAfter(node,ref);
case "first":
if(ref.firstChild){
return dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
return true;
}
break;
default:
ref.appendChild(node);
return true;
}
};
dojo.dom.insertAtIndex=function(node,_24f,_250){
var _251=_24f.childNodes;
if(!_251.length){
_24f.appendChild(node);
return true;
}
var _252=null;
for(var i=0;i<_251.length;i++){
var _254=_251.item(i)["getAttribute"]?parseInt(_251.item(i).getAttribute("dojoinsertionindex")):-1;
if(_254<_250){
_252=_251.item(i);
}
}
if(_252){
return dojo.dom.insertAfter(node,_252);
}else{
return dojo.dom.insertBefore(node,_251.item(0));
}
};
dojo.dom.textContent=function(node,text){
if(arguments.length>1){
var _257=dojo.doc();
dojo.dom.replaceChildren(node,_257.createTextNode(text));
return text;
}else{
if(node.textContent!=undefined){
return node.textContent;
}
var _258="";
if(node==null){
return _258;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_258+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_258+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _258;
}
};
dojo.dom.hasParent=function(node){
return node&&node.parentNode&&dojo.dom.isNode(node.parentNode);
};
dojo.dom.isTag=function(node){
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName==String(arguments[i])){
return String(arguments[i]);
}
}
}
return "";
};
dojo.dom.setAttributeNS=function(elem,_25e,_25f,_260){
if(elem==null||((elem==undefined)&&(typeof elem=="undefined"))){
dojo.raise("No element given to dojo.dom.setAttributeNS");
}
if(!((elem.setAttributeNS==undefined)&&(typeof elem.setAttributeNS=="undefined"))){
elem.setAttributeNS(_25e,_25f,_260);
}else{
var _261=elem.ownerDocument;
var _262=_261.createNode(2,_25f,_25e);
_262.nodeValue=_260;
elem.setAttributeNode(_262);
}
};
dojo.provide("dojo.html.common");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.html.body=function(){
dojo.deprecated("dojo.html.body() moved to dojo.body()","0.5");
return dojo.body();
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=dojo.global().event||{};
}
var t=(evt.srcElement?evt.srcElement:(evt.target?evt.target:null));
while((t)&&(t.nodeType!=1)){
t=t.parentNode;
}
return t;
};
dojo.html.getViewport=function(){
var _265=dojo.global();
var _266=dojo.doc();
var w=0;
var h=0;
if(dojo.render.html.mozilla){
w=_266.documentElement.clientWidth;
h=_265.innerHeight;
}else{
if(!dojo.render.html.opera&&_265.innerWidth){
w=_265.innerWidth;
h=_265.innerHeight;
}else{
if(!dojo.render.html.opera&&dojo.exists(_266,"documentElement.clientWidth")){
var w2=_266.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
h=_266.documentElement.clientHeight;
}else{
if(dojo.body().clientWidth){
w=dojo.body().clientWidth;
h=dojo.body().clientHeight;
}
}
}
}
return {width:w,height:h};
};
dojo.html.getScroll=function(){
var _26a=dojo.global();
var _26b=dojo.doc();
var top=_26a.pageYOffset||_26b.documentElement.scrollTop||dojo.body().scrollTop||0;
var left=_26a.pageXOffset||_26b.documentElement.scrollLeft||dojo.body().scrollLeft||0;
return {top:top,left:left,offset:{x:left,y:top}};
};
dojo.html.getParentByType=function(node,type){
var _270=dojo.doc();
var _271=dojo.byId(node);
type=type.toLowerCase();
while((_271)&&(_271.nodeName.toLowerCase()!=type)){
if(_271==(_270["body"]||_270["documentElement"])){
return null;
}
_271=_271.parentNode;
}
return _271;
};
dojo.html.getAttribute=function(node,attr){
node=dojo.byId(node);
if((!node)||(!node.getAttribute)){
return null;
}
var ta=typeof attr=="string"?attr:new String(attr);
var v=node.getAttribute(ta.toUpperCase());
if((v)&&(typeof v=="string")&&(v!="")){
return v;
}
if(v&&v.value){
return v.value;
}
if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
return (node.getAttributeNode(ta)).value;
}else{
if(node.getAttribute(ta)){
return node.getAttribute(ta);
}else{
if(node.getAttribute(ta.toLowerCase())){
return node.getAttribute(ta.toLowerCase());
}
}
}
return null;
};
dojo.html.hasAttribute=function(node,attr){
return dojo.html.getAttribute(dojo.byId(node),attr)?true:false;
};
dojo.html.getCursorPosition=function(e){
e=e||dojo.global().event;
var _279={x:0,y:0};
if(e.pageX||e.pageY){
_279.x=e.pageX;
_279.y=e.pageY;
}else{
var de=dojo.doc().documentElement;
var db=dojo.body();
_279.x=e.clientX+((de||db)["scrollLeft"])-((de||db)["clientLeft"]);
_279.y=e.clientY+((de||db)["scrollTop"])-((de||db)["clientTop"]);
}
return _279;
};
dojo.html.isTag=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName.toLowerCase()==String(arguments[i]).toLowerCase()){
return String(arguments[i]).toLowerCase();
}
}
}
return "";
};
if(dojo.render.html.ie&&!dojo.render.html.ie70){
if(window.location.href.substr(0,6).toLowerCase()!="https:"){
(function(){
var _27e=dojo.doc().createElement("script");
_27e.src="javascript:'dojo.html.createExternalElement=function(doc, tag){ return doc.createElement(tag); }'";
dojo.doc().getElementsByTagName("head")[0].appendChild(_27e);
})();
}
}else{
dojo.html.createExternalElement=function(doc,tag){
return doc.createElement(tag);
};
}
dojo.html._callDeprecated=function(_281,_282,args,_284,_285){
dojo.deprecated("dojo.html."+_281,"replaced by dojo.html."+_282+"("+(_284?"node, {"+_284+": "+_284+"}":"")+")"+(_285?"."+_285:""),"0.5");
var _286=[];
if(_284){
var _287={};
_287[_284]=args[1];
_286.push(args[0]);
_286.push(_287);
}else{
_286=args;
}
var ret=dojo.html[_282].apply(dojo.html,args);
if(_285){
return ret[_285];
}else{
return ret;
}
};
dojo.html.getViewportWidth=function(){
return dojo.html._callDeprecated("getViewportWidth","getViewport",arguments,null,"width");
};
dojo.html.getViewportHeight=function(){
return dojo.html._callDeprecated("getViewportHeight","getViewport",arguments,null,"height");
};
dojo.html.getViewportSize=function(){
return dojo.html._callDeprecated("getViewportSize","getViewport",arguments);
};
dojo.html.getScrollTop=function(){
return dojo.html._callDeprecated("getScrollTop","getScroll",arguments,null,"top");
};
dojo.html.getScrollLeft=function(){
return dojo.html._callDeprecated("getScrollLeft","getScroll",arguments,null,"left");
};
dojo.html.getScrollOffset=function(){
return dojo.html._callDeprecated("getScrollOffset","getScroll",arguments,null,"offset");
};
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.moduleUri=function(_28a,uri){
var loc=dojo.hostenv.getModulePrefix(_28a);
if(!loc){
return null;
}
if(loc.lastIndexOf("/")!=loc.length-1){
loc+="/";
}
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri()+loc,uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _28f=new dojo.uri.Uri(arguments[i].toString());
var _290=new dojo.uri.Uri(uri.toString());
if((_28f.path=="")&&(_28f.scheme==null)&&(_28f.authority==null)&&(_28f.query==null)){
if(_28f.fragment!=null){
_290.fragment=_28f.fragment;
}
_28f=_290;
}else{
if(_28f.scheme==null){
_28f.scheme=_290.scheme;
if(_28f.authority==null){
_28f.authority=_290.authority;
if(_28f.path.charAt(0)!="/"){
var path=_290.path.substring(0,_290.path.lastIndexOf("/")+1)+_28f.path;
var segs=path.split("/");
for(var j=0;j<segs.length;j++){
if(segs[j]=="."){
if(j==segs.length-1){
segs[j]="";
}else{
segs.splice(j,1);
j--;
}
}else{
if(j>0&&!(j==1&&segs[0]=="")&&segs[j]==".."&&segs[j-1]!=".."){
if(j==segs.length-1){
segs.splice(j,1);
segs[j-1]="";
}else{
segs.splice(j-1,2);
j-=2;
}
}
}
}
_28f.path=segs.join("/");
}
}
}
}
uri="";
if(_28f.scheme!=null){
uri+=_28f.scheme+":";
}
if(_28f.authority!=null){
uri+="//"+_28f.authority;
}
uri+=_28f.path;
if(_28f.query!=null){
uri+="?"+_28f.query;
}
if(_28f.fragment!=null){
uri+="#"+_28f.fragment;
}
}
this.uri=uri.toString();
var _294="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_294));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_294="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_294));
this.user=r[3]||null;
this.password=r[4]||null;
this.host=r[5];
this.port=r[7]||null;
}
this.toString=function(){
return this.uri;
};
};
};
dojo.provide("dojo.html.style");
dojo.html.getClass=function(node){
node=dojo.byId(node);
if(!node){
return "";
}
var cs="";
if(node.className){
cs=node.className;
}else{
if(dojo.html.hasAttribute(node,"class")){
cs=dojo.html.getAttribute(node,"class");
}
}
return cs.replace(/^\s+|\s+$/g,"");
};
dojo.html.getClasses=function(node){
var c=dojo.html.getClass(node);
return (c=="")?[]:c.split(/\s+/g);
};
dojo.html.hasClass=function(node,_29b){
return (new RegExp("(^|\\s+)"+_29b+"(\\s+|$)")).test(dojo.html.getClass(node));
};
dojo.html.prependClass=function(node,_29d){
_29d+=" "+dojo.html.getClass(node);
return dojo.html.setClass(node,_29d);
};
dojo.html.addClass=function(node,_29f){
if(dojo.html.hasClass(node,_29f)){
return false;
}
_29f=(dojo.html.getClass(node)+" "+_29f).replace(/^\s+|\s+$/g,"");
return dojo.html.setClass(node,_29f);
};
dojo.html.setClass=function(node,_2a1){
node=dojo.byId(node);
var cs=new String(_2a1);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_2a1);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("dojo.html.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_2a4,_2a5){
try{
if(!_2a5){
var _2a6=dojo.html.getClass(node).replace(new RegExp("(^|\\s+)"+_2a4+"(\\s+|$)"),"$1$2");
}else{
var _2a6=dojo.html.getClass(node).replace(_2a4,"");
}
dojo.html.setClass(node,_2a6);
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_2a8,_2a9){
dojo.html.removeClass(node,_2a9);
dojo.html.addClass(node,_2a8);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_2aa,_2ab,_2ac,_2ad,_2ae){
_2ae=false;
var _2af=dojo.doc();
_2ab=dojo.byId(_2ab)||_2af;
var _2b0=_2aa.split(/\s+/g);
var _2b1=[];
if(_2ad!=1&&_2ad!=2){
_2ad=0;
}
var _2b2=new RegExp("(\\s|^)(("+_2b0.join(")|(")+"))(\\s|$)");
var _2b3=_2b0.join(" ").length;
var _2b4=[];
if(!_2ae&&_2af.evaluate){
var _2b5=".//"+(_2ac||"*")+"[contains(";
if(_2ad!=dojo.html.classMatchType.ContainsAny){
_2b5+="concat(' ',@class,' '), ' "+_2b0.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')";
if(_2ad==2){
_2b5+=" and string-length(@class)="+_2b3+"]";
}else{
_2b5+="]";
}
}else{
_2b5+="concat(' ',@class,' '), ' "+_2b0.join(" ') or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _2b6=_2af.evaluate(_2b5,_2ab,null,XPathResult.ANY_TYPE,null);
var _2b7=_2b6.iterateNext();
while(_2b7){
try{
_2b4.push(_2b7);
_2b7=_2b6.iterateNext();
}
catch(e){
break;
}
}
return _2b4;
}else{
if(!_2ac){
_2ac="*";
}
_2b4=_2ab.getElementsByTagName(_2ac);
var node,i=0;
outer:
while(node=_2b4[i++]){
var _2ba=dojo.html.getClasses(node);
if(_2ba.length==0){
continue outer;
}
var _2bb=0;
for(var j=0;j<_2ba.length;j++){
if(_2b2.test(_2ba[j])){
if(_2ad==dojo.html.classMatchType.ContainsAny){
_2b1.push(node);
continue outer;
}else{
_2bb++;
}
}else{
if(_2ad==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_2bb==_2b0.length){
if((_2ad==dojo.html.classMatchType.IsOnly)&&(_2bb==_2ba.length)){
_2b1.push(node);
}else{
if(_2ad==dojo.html.classMatchType.ContainsAll){
_2b1.push(node);
}
}
}
}
return _2b1;
}
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.toCamelCase=function(_2bd){
var arr=_2bd.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.html.toSelectorCase=function(_2c1){
return _2c1.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.html.getComputedStyle=function(node,_2c3,_2c4){
node=dojo.byId(node);
var _2c3=dojo.html.toSelectorCase(_2c3);
var _2c5=dojo.html.toCamelCase(_2c3);
if(!node||!node.style){
return _2c4;
}else{
if(document.defaultView&&dojo.html.isDescendantOf(node,node.ownerDocument)){
try{
var cs=document.defaultView.getComputedStyle(node,"");
if(cs){
return cs.getPropertyValue(_2c3);
}
}
catch(e){
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_2c3);
}else{
return _2c4;
}
}
}else{
if(node.currentStyle){
return node.currentStyle[_2c5];
}
}
}
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_2c3);
}else{
return _2c4;
}
};
dojo.html.getStyleProperty=function(node,_2c8){
node=dojo.byId(node);
return (node&&node.style?node.style[dojo.html.toCamelCase(_2c8)]:undefined);
};
dojo.html.getStyle=function(node,_2ca){
var _2cb=dojo.html.getStyleProperty(node,_2ca);
return (_2cb?_2cb:dojo.html.getComputedStyle(node,_2ca));
};
dojo.html.setStyle=function(node,_2cd,_2ce){
node=dojo.byId(node);
if(node&&node.style){
var _2cf=dojo.html.toCamelCase(_2cd);
node.style[_2cf]=_2ce;
}
};
dojo.html.setStyleText=function(_2d0,text){
try{
_2d0.style.cssText=text;
}
catch(e){
_2d0.setAttribute("style",text);
}
};
dojo.html.copyStyle=function(_2d2,_2d3){
if(!_2d3.style.cssText){
_2d2.setAttribute("style",_2d3.getAttribute("style"));
}else{
_2d2.style.cssText=_2d3.style.cssText;
}
dojo.html.addClass(_2d2,dojo.html.getClass(_2d3));
};
dojo.html.getUnitValue=function(node,_2d5,_2d6){
var s=dojo.html.getComputedStyle(node,_2d5);
if((!s)||((s=="auto")&&(_2d6))){
return {value:0,units:"px"};
}
var _2d8=s.match(/(\-?[\d.]+)([a-z%]*)/i);
if(!_2d8){
return dojo.html.getUnitValue.bad;
}
return {value:Number(_2d8[1]),units:_2d8[2].toLowerCase()};
};
dojo.html.getUnitValue.bad={value:NaN,units:""};
dojo.html.getPixelValue=function(node,_2da,_2db){
var _2dc=dojo.html.getUnitValue(node,_2da,_2db);
if(isNaN(_2dc.value)){
return 0;
}
if((_2dc.value)&&(_2dc.units!="px")){
return NaN;
}
return _2dc.value;
};
dojo.html.setPositivePixelValue=function(node,_2de,_2df){
if(isNaN(_2df)){
return false;
}
node.style[_2de]=Math.max(0,_2df)+"px";
return true;
};
dojo.html.styleSheet=null;
dojo.html.insertCssRule=function(_2e0,_2e1,_2e2){
if(!dojo.html.styleSheet){
if(document.createStyleSheet){
dojo.html.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
dojo.html.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(dojo.html.styleSheet.cssRules){
_2e2=dojo.html.styleSheet.cssRules.length;
}else{
if(dojo.html.styleSheet.rules){
_2e2=dojo.html.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.html.styleSheet.insertRule){
var rule=_2e0+" { "+_2e1+" }";
return dojo.html.styleSheet.insertRule(rule,_2e2);
}else{
if(dojo.html.styleSheet.addRule){
return dojo.html.styleSheet.addRule(_2e0,_2e1,_2e2);
}else{
return null;
}
}
};
dojo.html.removeCssRule=function(_2e4){
if(!dojo.html.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_2e4){
_2e4=dojo.html.styleSheet.rules.length;
dojo.html.styleSheet.removeRule(_2e4);
}
}else{
if(document.styleSheets[0]){
if(!_2e4){
_2e4=dojo.html.styleSheet.cssRules.length;
}
dojo.html.styleSheet.deleteRule(_2e4);
}
}
return true;
};
dojo.html._insertedCssFiles=[];
dojo.html.insertCssFile=function(URI,doc,_2e7,_2e8){
if(!URI){
return;
}
if(!doc){
doc=document;
}
var _2e9=dojo.hostenv.getText(URI,false,_2e8);
if(_2e9===null){
return;
}
_2e9=dojo.html.fixPathsInCssText(_2e9,URI);
if(_2e7){
var idx=-1,node,ent=dojo.html._insertedCssFiles;
for(var i=0;i<ent.length;i++){
if((ent[i].doc==doc)&&(ent[i].cssText==_2e9)){
idx=i;
node=ent[i].nodeRef;
break;
}
}
if(node){
var _2ee=doc.getElementsByTagName("style");
for(var i=0;i<_2ee.length;i++){
if(_2ee[i]==node){
return;
}
}
dojo.html._insertedCssFiles.shift(idx,1);
}
}
var _2ef=dojo.html.insertCssText(_2e9);
dojo.html._insertedCssFiles.push({"doc":doc,"cssText":_2e9,"nodeRef":_2ef});
if(_2ef&&djConfig.isDebug){
_2ef.setAttribute("dbgHref",URI);
}
return _2ef;
};
dojo.html.insertCssText=function(_2f0,doc,URI){
if(!_2f0){
return;
}
if(!doc){
doc=document;
}
if(URI){
_2f0=dojo.html.fixPathsInCssText(_2f0,URI);
}
var _2f3=doc.createElement("style");
_2f3.setAttribute("type","text/css");
var head=doc.getElementsByTagName("head")[0];
if(!head){
dojo.debug("No head tag in document, aborting styles");
return;
}else{
head.appendChild(_2f3);
}
if(_2f3.styleSheet){
_2f3.styleSheet.cssText=_2f0;
}else{
var _2f5=doc.createTextNode(_2f0);
_2f3.appendChild(_2f5);
}
return _2f3;
};
dojo.html.fixPathsInCssText=function(_2f6,URI){
function iefixPathsInCssText(){
var _2f8=/AlphaImageLoader\(src\=['"]([\t\s\w()\/.\\'"-:#=&?~]*)['"]/;
while(_2f9=_2f8.exec(_2f6)){
url=_2f9[1].replace(_2fb,"$2");
if(!_2fc.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_2f6.substring(0,_2f9.index)+"AlphaImageLoader(src='"+url+"'";
_2f6=_2f6.substr(_2f9.index+_2f9[0].length);
}
return str+_2f6;
}
if(!_2f6||!URI){
return;
}
var _2f9,str="",url="";
var _2fe=/url\(\s*([\t\s\w()\/.\\'"-:#=&?]+)\s*\)/;
var _2fc=/(file|https?|ftps?):\/\//;
var _2fb=/^[\s]*(['"]?)([\w()\/.\\'"-:#=&?]*)\1[\s]*?$/;
if(dojo.render.html.ie55||dojo.render.html.ie60){
_2f6=iefixPathsInCssText();
}
while(_2f9=_2fe.exec(_2f6)){
url=_2f9[1].replace(_2fb,"$2");
if(!_2fc.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_2f6.substring(0,_2f9.index)+"url("+url+")";
_2f6=_2f6.substr(_2f9.index+_2f9[0].length);
}
return str+_2f6;
};
dojo.html.setActiveStyleSheet=function(_2ff){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_2ff){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.applyBrowserClass=function(node){
var drh=dojo.render.html;
var _30b={dj_ie:drh.ie,dj_ie55:drh.ie55,dj_ie6:drh.ie60,dj_ie7:drh.ie70,dj_iequirks:drh.ie&&drh.quirks,dj_opera:drh.opera,dj_opera8:drh.opera&&(Math.floor(dojo.render.version)==8),dj_opera9:drh.opera&&(Math.floor(dojo.render.version)==9),dj_khtml:drh.khtml,dj_safari:drh.safari,dj_gecko:drh.mozilla};
for(var p in _30b){
if(_30b[p]){
dojo.html.addClass(node,p);
}
}
};
dojo.provide("dojo.html.*");
dojo.debug=function(){
if(!djConfig.isDebug){
return;
}
var args=arguments;
if(dj_undef("println",dojo.hostenv)){
dojo.raise("dojo.debug not available (yet?)");
}
var _30e=dj_global["jum"]&&!dj_global["jum"].isBrowser;
var s=[(_30e?"":"DEBUG: ")];
for(var i=0;i<args.length;++i){
if(!false&&args[i]&&args[i] instanceof Error){
var msg="["+args[i].name+": "+dojo.errorToString(args[i])+(args[i].fileName?", file: "+args[i].fileName:"")+(args[i].lineNumber?", line: "+args[i].lineNumber:"")+"]";
}else{
try{
var msg=String(args[i]);
}
catch(e){
if(dojo.render.html.ie){
var msg="[ActiveXObject]";
}else{
var msg="[unknown]";
}
}
}
s.push(msg);
}
dojo.hostenv.println(s.join(" "));
};
dojo.debugShallow=function(obj){
if(!djConfig.isDebug){
return;
}
dojo.debug("------------------------------------------------------------");
dojo.debug("Object: "+obj);
var _313=[];
for(var prop in obj){
try{
_313.push(prop+": "+obj[prop]);
}
catch(E){
_313.push(prop+": ERROR - "+E.message);
}
}
_313.sort();
for(var i=0;i<_313.length;i++){
dojo.debug(_313[i]);
}
dojo.debug("------------------------------------------------------------");
};
dojo.debugDeep=function(obj){
if(!djConfig.isDebug){
return;
}
if(!dojo.uri||!dojo.uri.dojoUri){
return dojo.debug("You'll need to load dojo.uri.* for deep debugging - sorry!");
}
if(!window.open){
return dojo.debug("Deep debugging is only supported in host environments with window.open");
}
var idx=dojo.debugDeep.debugVars.length;
dojo.debugDeep.debugVars.push(obj);
var url=new dojo.uri.Uri(location,dojo.uri.dojoUri("src/debug/deep.html?var="+idx)).toString();
var win=window.open(url,"_blank","width=600, height=400, resizable=yes, scrollbars=yes, status=yes");
try{
win.debugVar=obj;
}
catch(e){
}
};
dojo.debugDeep.debugVars=[];
dojo.provide("dojo.html.display");
dojo.html._toggle=function(node,_31b,_31c){
node=dojo.byId(node);
_31c(node,!_31b(node));
return _31b(node);
};
dojo.html.show=function(node){
node=dojo.byId(node);
if(dojo.html.getStyleProperty(node,"display")=="none"){
dojo.html.setStyle(node,"display",(node.dojoDisplayCache||""));
node.dojoDisplayCache=undefined;
}
};
dojo.html.hide=function(node){
node=dojo.byId(node);
if(typeof node["dojoDisplayCache"]=="undefined"){
var d=dojo.html.getStyleProperty(node,"display");
if(d!="none"){
node.dojoDisplayCache=d;
}
}
dojo.html.setStyle(node,"display","none");
};
dojo.html.setShowing=function(node,_321){
dojo.html[(_321?"show":"hide")](node);
};
dojo.html.isShowing=function(node){
return (dojo.html.getStyleProperty(node,"display")!="none");
};
dojo.html.toggleShowing=function(node){
return dojo.html._toggle(node,dojo.html.isShowing,dojo.html.setShowing);
};
dojo.html.displayMap={tr:"",td:"",th:"",img:"inline",span:"inline",input:"inline",button:"inline"};
dojo.html.suggestDisplayByTagName=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
var tag=node.tagName.toLowerCase();
return (tag in dojo.html.displayMap?dojo.html.displayMap[tag]:"block");
}
};
dojo.html.setDisplay=function(node,_327){
dojo.html.setStyle(node,"display",((_327 instanceof String||typeof _327=="string")?_327:(_327?dojo.html.suggestDisplayByTagName(node):"none")));
};
dojo.html.isDisplayed=function(node){
return (dojo.html.getComputedStyle(node,"display")!="none");
};
dojo.html.toggleDisplay=function(node){
return dojo.html._toggle(node,dojo.html.isDisplayed,dojo.html.setDisplay);
};
dojo.html.setVisibility=function(node,_32b){
dojo.html.setStyle(node,"visibility",((_32b instanceof String||typeof _32b=="string")?_32b:(_32b?"visible":"hidden")));
};
dojo.html.isVisible=function(node){
return (dojo.html.getComputedStyle(node,"visibility")!="hidden");
};
dojo.html.toggleVisibility=function(node){
return dojo.html._toggle(node,dojo.html.isVisible,dojo.html.setVisibility);
};
dojo.html.setOpacity=function(node,_32f,_330){
node=dojo.byId(node);
var h=dojo.render.html;
if(!_330){
if(_32f>=1){
if(h.ie){
dojo.html.clearOpacity(node);
return;
}else{
_32f=0.999999;
}
}else{
if(_32f<0){
_32f=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_32f*100+")";
}
}
node.style.filter="Alpha(Opacity="+_32f*100+")";
}else{
if(h.moz){
node.style.opacity=_32f;
node.style.MozOpacity=_32f;
}else{
if(h.safari){
node.style.opacity=_32f;
node.style.KhtmlOpacity=_32f;
}else{
node.style.opacity=_32f;
}
}
}
};
dojo.html.clearOpacity=function(node){
node=dojo.byId(node);
var ns=node.style;
var h=dojo.render.html;
if(h.ie){
try{
if(node.filters&&node.filters.alpha){
ns.filter="";
}
}
catch(e){
}
}else{
if(h.moz){
ns.opacity=1;
ns.MozOpacity=1;
}else{
if(h.safari){
ns.opacity=1;
ns.KhtmlOpacity=1;
}else{
ns.opacity=1;
}
}
}
};
dojo.html.getOpacity=function(node){
node=dojo.byId(node);
var h=dojo.render.html;
if(h.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
dojo.provide("dojo.html.layout");
dojo.html.sumAncestorProperties=function(node,prop){
node=dojo.byId(node);
if(!node){
return 0;
}
var _33c=0;
while(node){
if(dojo.html.getComputedStyle(node,"position")=="fixed"){
return 0;
}
var val=node[prop];
if(val){
_33c+=val-0;
if(node==dojo.body()){
break;
}
}
node=node.parentNode;
}
return _33c;
};
dojo.html.setStyleAttributes=function(node,_33f){
node=dojo.byId(node);
var _340=_33f.replace(/(;)?\s*$/,"").split(";");
for(var i=0;i<_340.length;i++){
var _342=_340[i].split(":");
var name=_342[0].replace(/\s*$/,"").replace(/^\s*/,"").toLowerCase();
var _344=_342[1].replace(/\s*$/,"").replace(/^\s*/,"");
switch(name){
case "opacity":
dojo.html.setOpacity(node,_344);
break;
case "content-height":
dojo.html.setContentBox(node,{height:_344});
break;
case "content-width":
dojo.html.setContentBox(node,{width:_344});
break;
case "outer-height":
dojo.html.setMarginBox(node,{height:_344});
break;
case "outer-width":
dojo.html.setMarginBox(node,{width:_344});
break;
default:
node.style[dojo.html.toCamelCase(name)]=_344;
}
}
};
dojo.html.boxSizing={MARGIN_BOX:"margin-box",BORDER_BOX:"border-box",PADDING_BOX:"padding-box",CONTENT_BOX:"content-box"};
dojo.html.getAbsolutePosition=dojo.html.abs=function(node,_346,_347){
node=dojo.byId(node,node.ownerDocument);
var ret={x:0,y:0};
var bs=dojo.html.boxSizing;
if(!_347){
_347=bs.CONTENT_BOX;
}
var _34a=2;
var _34b;
switch(_347){
case bs.MARGIN_BOX:
_34b=3;
break;
case bs.BORDER_BOX:
_34b=2;
break;
case bs.PADDING_BOX:
default:
_34b=1;
break;
case bs.CONTENT_BOX:
_34b=0;
break;
}
var h=dojo.render.html;
var db=document["body"]||document["documentElement"];
if(h.ie){
with(node.getBoundingClientRect()){
ret.x=left-2;
ret.y=top-2;
}
}else{
if(document.getBoxObjectFor){
_34a=1;
try{
var bo=document.getBoxObjectFor(node);
ret.x=bo.x-dojo.html.sumAncestorProperties(node,"scrollLeft");
ret.y=bo.y-dojo.html.sumAncestorProperties(node,"scrollTop");
}
catch(e){
}
}else{
if(node["offsetParent"]){
var _34f;
if((h.safari)&&(node.style.getPropertyValue("position")=="absolute")&&(node.parentNode==db)){
_34f=db;
}else{
_34f=db.parentNode;
}
if(node.parentNode!=db){
var nd=node;
if(dojo.render.html.opera){
nd=db;
}
ret.x-=dojo.html.sumAncestorProperties(nd,"scrollLeft");
ret.y-=dojo.html.sumAncestorProperties(nd,"scrollTop");
}
var _351=node;
do{
var n=_351["offsetLeft"];
if(!h.opera||n>0){
ret.x+=isNaN(n)?0:n;
}
var m=_351["offsetTop"];
ret.y+=isNaN(m)?0:m;
_351=_351.offsetParent;
}while((_351!=_34f)&&(_351!=null));
}else{
if(node["x"]&&node["y"]){
ret.x+=isNaN(node.x)?0:node.x;
ret.y+=isNaN(node.y)?0:node.y;
}
}
}
}
if(_346){
var _354=dojo.html.getScroll();
ret.y+=_354.top;
ret.x+=_354.left;
}
var _355=[dojo.html.getPaddingExtent,dojo.html.getBorderExtent,dojo.html.getMarginExtent];
if(_34a>_34b){
for(var i=_34b;i<_34a;++i){
ret.y+=_355[i](node,"top");
ret.x+=_355[i](node,"left");
}
}else{
if(_34a<_34b){
for(var i=_34b;i>_34a;--i){
ret.y-=_355[i-1](node,"top");
ret.x-=_355[i-1](node,"left");
}
}
}
ret.top=ret.y;
ret.left=ret.x;
return ret;
};
dojo.html.isPositionAbsolute=function(node){
return (dojo.html.getComputedStyle(node,"position")=="absolute");
};
dojo.html._sumPixelValues=function(node,_359,_35a){
var _35b=0;
for(var x=0;x<_359.length;x++){
_35b+=dojo.html.getPixelValue(node,_359[x],_35a);
}
return _35b;
};
dojo.html.getMargin=function(node){
return {width:dojo.html._sumPixelValues(node,["margin-left","margin-right"],(dojo.html.getComputedStyle(node,"position")=="absolute")),height:dojo.html._sumPixelValues(node,["margin-top","margin-bottom"],(dojo.html.getComputedStyle(node,"position")=="absolute"))};
};
dojo.html.getBorder=function(node){
return {width:dojo.html.getBorderExtent(node,"left")+dojo.html.getBorderExtent(node,"right"),height:dojo.html.getBorderExtent(node,"top")+dojo.html.getBorderExtent(node,"bottom")};
};
dojo.html.getBorderExtent=function(node,side){
return (dojo.html.getStyle(node,"border-"+side+"-style")=="none"?0:dojo.html.getPixelValue(node,"border-"+side+"-width"));
};
dojo.html.getMarginExtent=function(node,side){
return dojo.html._sumPixelValues(node,["margin-"+side],dojo.html.isPositionAbsolute(node));
};
dojo.html.getPaddingExtent=function(node,side){
return dojo.html._sumPixelValues(node,["padding-"+side],true);
};
dojo.html.getPadding=function(node){
return {width:dojo.html._sumPixelValues(node,["padding-left","padding-right"],true),height:dojo.html._sumPixelValues(node,["padding-top","padding-bottom"],true)};
};
dojo.html.getPadBorder=function(node){
var pad=dojo.html.getPadding(node);
var _368=dojo.html.getBorder(node);
return {width:pad.width+_368.width,height:pad.height+_368.height};
};
dojo.html.getBoxSizing=function(node){
var h=dojo.render.html;
var bs=dojo.html.boxSizing;
if((h.ie)||(h.opera)){
var cm=document["compatMode"];
if((cm=="BackCompat")||(cm=="QuirksMode")){
return bs.BORDER_BOX;
}else{
return bs.CONTENT_BOX;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _36d=dojo.html.getStyle(node,"-moz-box-sizing");
if(!_36d){
_36d=dojo.html.getStyle(node,"box-sizing");
}
return (_36d?_36d:bs.CONTENT_BOX);
}
};
dojo.html.isBorderBox=function(node){
return (dojo.html.getBoxSizing(node)==dojo.html.boxSizing.BORDER_BOX);
};
dojo.html.getBorderBox=function(node){
node=dojo.byId(node);
return {width:node.offsetWidth,height:node.offsetHeight};
};
dojo.html.getPaddingBox=function(node){
var box=dojo.html.getBorderBox(node);
var _372=dojo.html.getBorder(node);
return {width:box.width-_372.width,height:box.height-_372.height};
};
dojo.html.getContentBox=function(node){
node=dojo.byId(node);
var _374=dojo.html.getPadBorder(node);
return {width:node.offsetWidth-_374.width,height:node.offsetHeight-_374.height};
};
dojo.html.setContentBox=function(node,args){
node=dojo.byId(node);
var _377=0;
var _378=0;
var isbb=dojo.html.isBorderBox(node);
var _37a=(isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var ret={};
if(typeof args.width!="undefined"){
_377=args.width+_37a.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_377);
}
if(typeof args.height!="undefined"){
_378=args.height+_37a.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_378);
}
return ret;
};
dojo.html.getMarginBox=function(node){
var _37d=dojo.html.getBorderBox(node);
var _37e=dojo.html.getMargin(node);
return {width:_37d.width+_37e.width,height:_37d.height+_37e.height};
};
dojo.html.setMarginBox=function(node,args){
node=dojo.byId(node);
var _381=0;
var _382=0;
var isbb=dojo.html.isBorderBox(node);
var _384=(!isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var _385=dojo.html.getMargin(node);
var ret={};
if(typeof args.width!="undefined"){
_381=args.width-_384.width;
_381-=_385.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_381);
}
if(typeof args.height!="undefined"){
_382=args.height-_384.height;
_382-=_385.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_382);
}
return ret;
};
dojo.html.getElementBox=function(node,type){
var bs=dojo.html.boxSizing;
switch(type){
case bs.MARGIN_BOX:
return dojo.html.getMarginBox(node);
case bs.BORDER_BOX:
return dojo.html.getBorderBox(node);
case bs.PADDING_BOX:
return dojo.html.getPaddingBox(node);
case bs.CONTENT_BOX:
default:
return dojo.html.getContentBox(node);
}
};
dojo.html.toCoordinateObject=dojo.html.toCoordinateArray=function(_38a,_38b,_38c){
if(_38a instanceof Array||typeof _38a=="array"){
dojo.deprecated("dojo.html.toCoordinateArray","use dojo.html.toCoordinateObject({left: , top: , width: , height: }) instead","0.5");
while(_38a.length<4){
_38a.push(0);
}
while(_38a.length>4){
_38a.pop();
}
var ret={left:_38a[0],top:_38a[1],width:_38a[2],height:_38a[3]};
}else{
if(!_38a.nodeType&&!(_38a instanceof String||typeof _38a=="string")&&("width" in _38a||"height" in _38a||"left" in _38a||"x" in _38a||"top" in _38a||"y" in _38a)){
var ret={left:_38a.left||_38a.x||0,top:_38a.top||_38a.y||0,width:_38a.width||0,height:_38a.height||0};
}else{
var node=dojo.byId(_38a);
var pos=dojo.html.abs(node,_38b,_38c);
var _390=dojo.html.getMarginBox(node);
var ret={left:pos.left,top:pos.top,width:_390.width,height:_390.height};
}
}
ret.x=ret.left;
ret.y=ret.top;
return ret;
};
dojo.html.setMarginBoxWidth=dojo.html.setOuterWidth=function(node,_392){
return dojo.html._callDeprecated("setMarginBoxWidth","setMarginBox",arguments,"width");
};
dojo.html.setMarginBoxHeight=dojo.html.setOuterHeight=function(){
return dojo.html._callDeprecated("setMarginBoxHeight","setMarginBox",arguments,"height");
};
dojo.html.getMarginBoxWidth=dojo.html.getOuterWidth=function(){
return dojo.html._callDeprecated("getMarginBoxWidth","getMarginBox",arguments,null,"width");
};
dojo.html.getMarginBoxHeight=dojo.html.getOuterHeight=function(){
return dojo.html._callDeprecated("getMarginBoxHeight","getMarginBox",arguments,null,"height");
};
dojo.html.getTotalOffset=function(node,type,_395){
return dojo.html._callDeprecated("getTotalOffset","getAbsolutePosition",arguments,null,type);
};
dojo.html.getAbsoluteX=function(node,_397){
return dojo.html._callDeprecated("getAbsoluteX","getAbsolutePosition",arguments,null,"x");
};
dojo.html.getAbsoluteY=function(node,_399){
return dojo.html._callDeprecated("getAbsoluteY","getAbsolutePosition",arguments,null,"y");
};
dojo.html.totalOffsetLeft=function(node,_39b){
return dojo.html._callDeprecated("totalOffsetLeft","getAbsolutePosition",arguments,null,"left");
};
dojo.html.totalOffsetTop=function(node,_39d){
return dojo.html._callDeprecated("totalOffsetTop","getAbsolutePosition",arguments,null,"top");
};
dojo.html.getMarginWidth=function(node){
return dojo.html._callDeprecated("getMarginWidth","getMargin",arguments,null,"width");
};
dojo.html.getMarginHeight=function(node){
return dojo.html._callDeprecated("getMarginHeight","getMargin",arguments,null,"height");
};
dojo.html.getBorderWidth=function(node){
return dojo.html._callDeprecated("getBorderWidth","getBorder",arguments,null,"width");
};
dojo.html.getBorderHeight=function(node){
return dojo.html._callDeprecated("getBorderHeight","getBorder",arguments,null,"height");
};
dojo.html.getPaddingWidth=function(node){
return dojo.html._callDeprecated("getPaddingWidth","getPadding",arguments,null,"width");
};
dojo.html.getPaddingHeight=function(node){
return dojo.html._callDeprecated("getPaddingHeight","getPadding",arguments,null,"height");
};
dojo.html.getPadBorderWidth=function(node){
return dojo.html._callDeprecated("getPadBorderWidth","getPadBorder",arguments,null,"width");
};
dojo.html.getPadBorderHeight=function(node){
return dojo.html._callDeprecated("getPadBorderHeight","getPadBorder",arguments,null,"height");
};
dojo.html.getBorderBoxWidth=dojo.html.getInnerWidth=function(){
return dojo.html._callDeprecated("getBorderBoxWidth","getBorderBox",arguments,null,"width");
};
dojo.html.getBorderBoxHeight=dojo.html.getInnerHeight=function(){
return dojo.html._callDeprecated("getBorderBoxHeight","getBorderBox",arguments,null,"height");
};
dojo.html.getContentBoxWidth=dojo.html.getContentWidth=function(){
return dojo.html._callDeprecated("getContentBoxWidth","getContentBox",arguments,null,"width");
};
dojo.html.getContentBoxHeight=dojo.html.getContentHeight=function(){
return dojo.html._callDeprecated("getContentBoxHeight","getContentBox",arguments,null,"height");
};
dojo.html.setContentBoxWidth=dojo.html.setContentWidth=function(node,_3a7){
return dojo.html._callDeprecated("setContentBoxWidth","setContentBox",arguments,"width");
};
dojo.html.setContentBoxHeight=dojo.html.setContentHeight=function(node,_3a9){
return dojo.html._callDeprecated("setContentBoxHeight","setContentBox",arguments,"height");
};
dojo.provide("dojo.html.util");
dojo.html.getElementWindow=function(_3aa){
return dojo.html.getDocumentWindow(_3aa.ownerDocument);
};
dojo.html.getDocumentWindow=function(doc){
if(dojo.render.html.safari&&!doc._parentWindow){
var fix=function(win){
win.document._parentWindow=win;
for(var i=0;i<win.frames.length;i++){
fix(win.frames[i]);
}
};
fix(window.top);
}
if(dojo.render.html.ie&&window!==document.parentWindow&&!doc._parentWindow){
doc.parentWindow.execScript("document._parentWindow = window;","Javascript");
var win=doc._parentWindow;
doc._parentWindow=null;
return win;
}
return doc._parentWindow||doc.parentWindow||doc.defaultView;
};
dojo.html.gravity=function(node,e){
node=dojo.byId(node);
var _3b2=dojo.html.getCursorPosition(e);
with(dojo.html){
var _3b3=getAbsolutePosition(node,true);
var bb=getBorderBox(node);
var _3b5=_3b3.x+(bb.width/2);
var _3b6=_3b3.y+(bb.height/2);
}
with(dojo.html.gravity){
return ((_3b2.x<_3b5?WEST:EAST)|(_3b2.y<_3b6?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_3b7,e){
_3b7=dojo.byId(_3b7);
var _3b9=dojo.html.getCursorPosition(e);
var bb=dojo.html.getBorderBox(_3b7);
var _3bb=dojo.html.getAbsolutePosition(_3b7,true,dojo.html.boxSizing.BORDER_BOX);
var top=_3bb.y;
var _3bd=top+bb.height;
var left=_3bb.x;
var _3bf=left+bb.width;
return (_3b9.x>=left&&_3b9.x<=_3bf&&_3b9.y>=top&&_3b9.y<=_3bd);
};
dojo.html.renderedTextContent=function(node){
node=dojo.byId(node);
var _3c1="";
if(node==null){
return _3c1;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
var _3c3="unknown";
try{
_3c3=dojo.html.getStyle(node.childNodes[i],"display");
}
catch(E){
}
switch(_3c3){
case "block":
case "list-item":
case "run-in":
case "table":
case "table-row-group":
case "table-header-group":
case "table-footer-group":
case "table-row":
case "table-column-group":
case "table-column":
case "table-cell":
case "table-caption":
_3c1+="\n";
_3c1+=dojo.html.renderedTextContent(node.childNodes[i]);
_3c1+="\n";
break;
case "none":
break;
default:
if(node.childNodes[i].tagName&&node.childNodes[i].tagName.toLowerCase()=="br"){
_3c1+="\n";
}else{
_3c1+=dojo.html.renderedTextContent(node.childNodes[i]);
}
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
var _3c5="unknown";
try{
_3c5=dojo.html.getStyle(node,"text-transform");
}
catch(E){
}
switch(_3c5){
case "capitalize":
var _3c6=text.split(" ");
for(var i=0;i<_3c6.length;i++){
_3c6[i]=_3c6[i].charAt(0).toUpperCase()+_3c6[i].substring(1);
}
text=_3c6.join(" ");
break;
case "uppercase":
text=text.toUpperCase();
break;
case "lowercase":
text=text.toLowerCase();
break;
default:
break;
}
switch(_3c5){
case "nowrap":
break;
case "pre-wrap":
break;
case "pre-line":
break;
case "pre":
break;
default:
text=text.replace(/\s+/," ");
if(/\s$/.test(_3c1)){
text.replace(/^\s/,"");
}
break;
}
_3c1+=text;
break;
default:
break;
}
}
return _3c1;
};
dojo.html.createNodesFromText=function(txt,trim){
if(trim){
txt=txt.replace(/^\s+|\s+$/g,"");
}
var tn=dojo.doc().createElement("div");
tn.style.visibility="hidden";
dojo.body().appendChild(tn);
var _3ca="none";
if((/^<t[dh][\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody><tr>"+txt+"</tr></tbody></table>";
_3ca="cell";
}else{
if((/^<tr[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody>"+txt+"</tbody></table>";
_3ca="row";
}else{
if((/^<(thead|tbody|tfoot)[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table>"+txt+"</table>";
_3ca="section";
}
}
}
tn.innerHTML=txt;
if(tn["normalize"]){
tn.normalize();
}
var _3cb=null;
switch(_3ca){
case "cell":
_3cb=tn.getElementsByTagName("tr")[0];
break;
case "row":
_3cb=tn.getElementsByTagName("tbody")[0];
break;
case "section":
_3cb=tn.getElementsByTagName("table")[0];
break;
default:
_3cb=tn;
break;
}
var _3cc=[];
for(var x=0;x<_3cb.childNodes.length;x++){
_3cc.push(_3cb.childNodes[x].cloneNode(true));
}
tn.style.display="none";
dojo.body().removeChild(tn);
return _3cc;
};
dojo.html.placeOnScreen=function(node,_3cf,_3d0,_3d1,_3d2,_3d3,_3d4){
if(_3cf instanceof Array||typeof _3cf=="array"){
_3d4=_3d3;
_3d3=_3d2;
_3d2=_3d1;
_3d1=_3d0;
_3d0=_3cf[1];
_3cf=_3cf[0];
}
if(_3d3 instanceof String||typeof _3d3=="string"){
_3d3=_3d3.split(",");
}
if(!isNaN(_3d1)){
_3d1=[Number(_3d1),Number(_3d1)];
}else{
if(!(_3d1 instanceof Array||typeof _3d1=="array")){
_3d1=[0,0];
}
}
var _3d5=dojo.html.getScroll().offset;
var view=dojo.html.getViewport();
node=dojo.byId(node);
var _3d7=node.style.display;
node.style.display="";
var bb=dojo.html.getBorderBox(node);
var w=bb.width;
var h=bb.height;
node.style.display=_3d7;
if(!(_3d3 instanceof Array||typeof _3d3=="array")){
_3d3=["TL"];
}
var _3db,_3dc,_3dd=Infinity,_3de;
for(var _3df=0;_3df<_3d3.length;++_3df){
var _3e0=_3d3[_3df];
var _3e1=true;
var tryX=_3cf-(_3e0.charAt(1)=="L"?0:w)+_3d1[0]*(_3e0.charAt(1)=="L"?1:-1);
var tryY=_3d0-(_3e0.charAt(0)=="T"?0:h)+_3d1[1]*(_3e0.charAt(0)=="T"?1:-1);
if(_3d2){
tryX-=_3d5.x;
tryY-=_3d5.y;
}
if(tryX<0){
tryX=0;
_3e1=false;
}
if(tryY<0){
tryY=0;
_3e1=false;
}
var x=tryX+w;
if(x>view.width){
x=view.width-w;
_3e1=false;
}else{
x=tryX;
}
x=Math.max(_3d1[0],x)+_3d5.x;
var y=tryY+h;
if(y>view.height){
y=view.height-h;
_3e1=false;
}else{
y=tryY;
}
y=Math.max(_3d1[1],y)+_3d5.y;
if(_3e1){
_3db=x;
_3dc=y;
_3dd=0;
_3de=_3e0;
break;
}else{
var dist=Math.pow(x-tryX-_3d5.x,2)+Math.pow(y-tryY-_3d5.y,2);
if(_3dd>dist){
_3dd=dist;
_3db=x;
_3dc=y;
_3de=_3e0;
}
}
}
if(!_3d4){
node.style.left=_3db+"px";
node.style.top=_3dc+"px";
}
return {left:_3db,top:_3dc,x:_3db,y:_3dc,dist:_3dd,corner:_3de};
};
dojo.html.placeOnScreenPoint=function(node,_3e8,_3e9,_3ea,_3eb){
dojo.deprecated("dojo.html.placeOnScreenPoint","use dojo.html.placeOnScreen() instead","0.5");
return dojo.html.placeOnScreen(node,_3e8,_3e9,_3ea,_3eb,["TL","TR","BL","BR"]);
};
dojo.html.placeOnScreenAroundElement=function(node,_3ed,_3ee,_3ef,_3f0,_3f1){
var best,_3f3=Infinity;
_3ed=dojo.byId(_3ed);
var _3f4=_3ed.style.display;
_3ed.style.display="";
var mb=dojo.html.getElementBox(_3ed,_3ef);
var _3f6=mb.width;
var _3f7=mb.height;
var _3f8=dojo.html.getAbsolutePosition(_3ed,true,_3ef);
_3ed.style.display=_3f4;
for(var _3f9 in _3f0){
var pos,_3fb,_3fc;
var _3fd=_3f0[_3f9];
_3fb=_3f8.x+(_3f9.charAt(1)=="L"?0:_3f6);
_3fc=_3f8.y+(_3f9.charAt(0)=="T"?0:_3f7);
pos=dojo.html.placeOnScreen(node,_3fb,_3fc,_3ee,true,_3fd,true);
if(pos.dist==0){
best=pos;
break;
}else{
if(_3f3>pos.dist){
_3f3=pos.dist;
best=pos;
}
}
}
if(!_3f1){
node.style.left=best.left+"px";
node.style.top=best.top+"px";
}
return best;
};
dojo.html.scrollIntoView=function(node){
if(!node){
return;
}
if(dojo.render.html.ie){
if(dojo.html.getBorderBox(node.parentNode).height<node.parentNode.scrollHeight){
node.scrollIntoView(false);
}
}else{
if(dojo.render.html.mozilla){
node.scrollIntoView(false);
}else{
var _3ff=node.parentNode;
var _400=_3ff.scrollTop+dojo.html.getBorderBox(_3ff).height;
var _401=node.offsetTop+dojo.html.getMarginBox(node).height;
if(_400<_401){
_3ff.scrollTop+=(_401-_400);
}else{
if(_3ff.scrollTop>node.offsetTop){
_3ff.scrollTop-=(_3ff.scrollTop-node.offsetTop);
}
}
}
}
};
dojo.provide("dojo.gfx.color");
dojo.gfx.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.gfx.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=g||1;
}else{
if(r instanceof dojo.gfx.color.Color){
this.r=r.r;
this.b=r.b;
this.g=r.g;
this.a=r.a;
}else{
this.r=r;
this.g=g;
this.b=b;
this.a=a;
}
}
}
};
dojo.gfx.color.Color.fromArray=function(arr){
return new dojo.gfx.color.Color(arr[0],arr[1],arr[2],arr[3]);
};
dojo.extend(dojo.gfx.color.Color,{toRgb:function(_408){
if(_408){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.gfx.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},blend:function(_409,_40a){
var rgb=null;
if(dojo.lang.isArray(_409)){
rgb=_409;
}else{
if(_409 instanceof dojo.gfx.color.Color){
rgb=_409.toRgb();
}else{
rgb=new dojo.gfx.color.Color(_409).toRgb();
}
}
return dojo.gfx.color.blend(this.toRgb(),rgb,_40a);
}});
dojo.gfx.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],lime:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.gfx.color.blend=function(a,b,_40e){
if(typeof a=="string"){
return dojo.gfx.color.blendHex(a,b,_40e);
}
if(!_40e){
_40e=0;
}
_40e=Math.min(Math.max(-1,_40e),1);
_40e=((_40e+1)/2);
var c=[];
for(var x=0;x<3;x++){
c[x]=parseInt(b[x]+((a[x]-b[x])*_40e));
}
return c;
};
dojo.gfx.color.blendHex=function(a,b,_413){
return dojo.gfx.color.rgb2hex(dojo.gfx.color.blend(dojo.gfx.color.hex2rgb(a),dojo.gfx.color.hex2rgb(b),_413));
};
dojo.gfx.color.extractRGB=function(_414){
var hex="0123456789abcdef";
_414=_414.toLowerCase();
if(_414.indexOf("rgb")==0){
var _416=_414.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_416.splice(1,3);
return ret;
}else{
var _418=dojo.gfx.color.hex2rgb(_414);
if(_418){
return _418;
}else{
return dojo.gfx.color.named[_414]||[255,255,255];
}
}
};
dojo.gfx.color.hex2rgb=function(hex){
var _41a="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_41a+"]","g"),"")!=""){
return null;
}
if(hex.length==3){
rgb[0]=hex.charAt(0)+hex.charAt(0);
rgb[1]=hex.charAt(1)+hex.charAt(1);
rgb[2]=hex.charAt(2)+hex.charAt(2);
}else{
rgb[0]=hex.substring(0,2);
rgb[1]=hex.substring(2,4);
rgb[2]=hex.substring(4);
}
for(var i=0;i<rgb.length;i++){
rgb[i]=_41a.indexOf(rgb[i].charAt(0))*16+_41a.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.gfx.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
var ret=dojo.lang.map([r,g,b],function(x){
x=new Number(x);
var s=x.toString(16);
while(s.length<2){
s="0"+s;
}
return s;
});
ret.unshift("#");
return ret.join("");
};
dojo.provide("dojo.lfx.Animation");
dojo.lfx.Line=function(_423,end){
this.start=_423;
this.end=end;
if(dojo.lang.isArray(_423)){
var diff=[];
dojo.lang.forEach(this.start,function(s,i){
diff[i]=this.end[i]-s;
},this);
this.getValue=function(n){
var res=[];
dojo.lang.forEach(this.start,function(s,i){
res[i]=(diff[i]*n)+s;
},this);
return res;
};
}else{
var diff=end-_423;
this.getValue=function(n){
return (diff*n)+this.start;
};
}
};
dojo.lfx.easeDefault=function(n){
if(dojo.render.html.khtml){
return (parseFloat("0.5")+((Math.sin((n+parseFloat("1.5"))*Math.PI))/2));
}else{
return (0.5+((Math.sin((n+1.5)*Math.PI))/2));
}
};
dojo.lfx.easeIn=function(n){
return Math.pow(n,3);
};
dojo.lfx.easeOut=function(n){
return (1-Math.pow(1-n,3));
};
dojo.lfx.easeInOut=function(n){
return ((3*Math.pow(n,2))-(2*Math.pow(n,3)));
};
dojo.lfx.IAnimation=function(){
};
dojo.lang.extend(dojo.lfx.IAnimation,{curve:null,duration:1000,easing:null,repeatCount:0,rate:25,handler:null,beforeBegin:null,onBegin:null,onAnimate:null,onEnd:null,onPlay:null,onPause:null,onStop:null,play:null,pause:null,stop:null,connect:function(evt,_432,_433){
if(!_433){
_433=_432;
_432=this;
}
_433=dojo.lang.hitch(_432,_433);
var _434=this[evt]||function(){
};
this[evt]=function(){
var ret=_434.apply(this,arguments);
_433.apply(this,arguments);
return ret;
};
return this;
},fire:function(evt,args){
if(this[evt]){
this[evt].apply(this,(args||[]));
}
return this;
},repeat:function(_438){
this.repeatCount=_438;
return this;
},_active:false,_paused:false});
dojo.lfx.Animation=function(_439,_43a,_43b,_43c,_43d,rate){
dojo.lfx.IAnimation.call(this);
if(dojo.lang.isNumber(_439)||(!_439&&_43a.getValue)){
rate=_43d;
_43d=_43c;
_43c=_43b;
_43b=_43a;
_43a=_439;
_439=null;
}else{
if(_439.getValue||dojo.lang.isArray(_439)){
rate=_43c;
_43d=_43b;
_43c=_43a;
_43b=_439;
_43a=null;
_439=null;
}
}
if(dojo.lang.isArray(_43b)){
this.curve=new dojo.lfx.Line(_43b[0],_43b[1]);
}else{
this.curve=_43b;
}
if(_43a!=null&&_43a>0){
this.duration=_43a;
}
if(_43d){
this.repeatCount=_43d;
}
if(rate){
this.rate=rate;
}
if(_439){
dojo.lang.forEach(["handler","beforeBegin","onBegin","onEnd","onPlay","onStop","onAnimate"],function(item){
if(_439[item]){
this.connect(item,_439[item]);
}
},this);
}
if(_43c&&dojo.lang.isFunction(_43c)){
this.easing=_43c;
}
};
dojo.inherits(dojo.lfx.Animation,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Animation,{_startTime:null,_endTime:null,_timer:null,_percent:0,_startRepeatCount:0,play:function(_440,_441){
if(_441){
clearTimeout(this._timer);
this._active=false;
this._paused=false;
this._percent=0;
}else{
if(this._active&&!this._paused){
return this;
}
}
this.fire("handler",["beforeBegin"]);
this.fire("beforeBegin");
if(_440>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_441);
}),_440);
return this;
}
this._startTime=new Date().valueOf();
if(this._paused){
this._startTime-=(this.duration*this._percent/100);
}
this._endTime=this._startTime+this.duration;
this._active=true;
this._paused=false;
var step=this._percent/100;
var _443=this.curve.getValue(step);
if(this._percent==0){
if(!this._startRepeatCount){
this._startRepeatCount=this.repeatCount;
}
this.fire("handler",["begin",_443]);
this.fire("onBegin",[_443]);
}
this.fire("handler",["play",_443]);
this.fire("onPlay",[_443]);
this._cycle();
return this;
},pause:function(){
clearTimeout(this._timer);
if(!this._active){
return this;
}
this._paused=true;
var _444=this.curve.getValue(this._percent/100);
this.fire("handler",["pause",_444]);
this.fire("onPause",[_444]);
return this;
},gotoPercent:function(pct,_446){
clearTimeout(this._timer);
this._active=true;
this._paused=true;
this._percent=pct;
if(_446){
this.play();
}
return this;
},stop:function(_447){
clearTimeout(this._timer);
var step=this._percent/100;
if(_447){
step=1;
}
var _449=this.curve.getValue(step);
this.fire("handler",["stop",_449]);
this.fire("onStop",[_449]);
this._active=false;
this._paused=false;
return this;
},status:function(){
if(this._active){
return this._paused?"paused":"playing";
}else{
return "stopped";
}
return this;
},_cycle:function(){
clearTimeout(this._timer);
if(this._active){
var curr=new Date().valueOf();
var step=(curr-this._startTime)/(this._endTime-this._startTime);
if(step>=1){
step=1;
this._percent=100;
}else{
this._percent=step*100;
}
if((this.easing)&&(dojo.lang.isFunction(this.easing))){
step=this.easing(step);
}
var _44c=this.curve.getValue(step);
this.fire("handler",["animate",_44c]);
this.fire("onAnimate",[_44c]);
if(step<1){
this._timer=setTimeout(dojo.lang.hitch(this,"_cycle"),this.rate);
}else{
this._active=false;
this.fire("handler",["end"]);
this.fire("onEnd");
if(this.repeatCount>0){
this.repeatCount--;
this.play(null,true);
}else{
if(this.repeatCount==-1){
this.play(null,true);
}else{
if(this._startRepeatCount){
this.repeatCount=this._startRepeatCount;
this._startRepeatCount=0;
}
}
}
}
}
return this;
}});
dojo.lfx.Combine=function(_44d){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._animsEnded=0;
var _44e=arguments;
if(_44e.length==1&&(dojo.lang.isArray(_44e[0])||dojo.lang.isArrayLike(_44e[0]))){
_44e=_44e[0];
}
dojo.lang.forEach(_44e,function(anim){
this._anims.push(anim);
anim.connect("onEnd",dojo.lang.hitch(this,"_onAnimsEnded"));
},this);
};
dojo.inherits(dojo.lfx.Combine,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Combine,{_animsEnded:0,play:function(_450,_451){
if(!this._anims.length){
return this;
}
this.fire("beforeBegin");
if(_450>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_451);
}),_450);
return this;
}
if(_451||this._anims[0].percent==0){
this.fire("onBegin");
}
this.fire("onPlay");
this._animsCall("play",null,_451);
return this;
},pause:function(){
this.fire("onPause");
this._animsCall("pause");
return this;
},stop:function(_452){
this.fire("onStop");
this._animsCall("stop",_452);
return this;
},_onAnimsEnded:function(){
this._animsEnded++;
if(this._animsEnded>=this._anims.length){
this.fire("onEnd");
}
return this;
},_animsCall:function(_453){
var args=[];
if(arguments.length>1){
for(var i=1;i<arguments.length;i++){
args.push(arguments[i]);
}
}
var _456=this;
dojo.lang.forEach(this._anims,function(anim){
anim[_453](args);
},_456);
return this;
}});
dojo.lfx.Chain=function(_458){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._currAnim=-1;
var _459=arguments;
if(_459.length==1&&(dojo.lang.isArray(_459[0])||dojo.lang.isArrayLike(_459[0]))){
_459=_459[0];
}
var _45a=this;
dojo.lang.forEach(_459,function(anim,i,_45d){
this._anims.push(anim);
if(i<_45d.length-1){
anim.connect("onEnd",dojo.lang.hitch(this,"_playNext"));
}else{
anim.connect("onEnd",dojo.lang.hitch(this,function(){
this.fire("onEnd");
}));
}
},this);
};
dojo.inherits(dojo.lfx.Chain,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Chain,{_currAnim:-1,play:function(_45e,_45f){
if(!this._anims.length){
return this;
}
if(_45f||!this._anims[this._currAnim]){
this._currAnim=0;
}
var _460=this._anims[this._currAnim];
this.fire("beforeBegin");
if(_45e>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_45f);
}),_45e);
return this;
}
if(_460){
if(this._currAnim==0){
this.fire("handler",["begin",this._currAnim]);
this.fire("onBegin",[this._currAnim]);
}
this.fire("onPlay",[this._currAnim]);
_460.play(null,_45f);
}
return this;
},pause:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].pause();
this.fire("onPause",[this._currAnim]);
}
return this;
},playPause:function(){
if(this._anims.length==0){
return this;
}
if(this._currAnim==-1){
this._currAnim=0;
}
var _461=this._anims[this._currAnim];
if(_461){
if(!_461._active||_461._paused){
this.play();
}else{
this.pause();
}
}
return this;
},stop:function(){
var _462=this._anims[this._currAnim];
if(_462){
_462.stop();
this.fire("onStop",[this._currAnim]);
}
return _462;
},_playNext:function(){
if(this._currAnim==-1||this._anims.length==0){
return this;
}
this._currAnim++;
if(this._anims[this._currAnim]){
this._anims[this._currAnim].play(null,true);
}
return this;
}});
dojo.lfx.combine=function(_463){
var _464=arguments;
if(dojo.lang.isArray(arguments[0])){
_464=arguments[0];
}
if(_464.length==1){
return _464[0];
}
return new dojo.lfx.Combine(_464);
};
dojo.lfx.chain=function(_465){
var _466=arguments;
if(dojo.lang.isArray(arguments[0])){
_466=arguments[0];
}
if(_466.length==1){
return _466[0];
}
return new dojo.lfx.Chain(_466);
};
dojo.provide("dojo.html.color");
dojo.html.getBackgroundColor=function(node){
node=dojo.byId(node);
var _468;
do{
_468=dojo.html.getStyle(node,"background-color");
if(_468.toLowerCase()=="rgba(0, 0, 0, 0)"){
_468="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(["transparent",""],_468));
if(_468=="transparent"){
_468=[255,255,255,0];
}else{
_468=dojo.gfx.color.extractRGB(_468);
}
return _468;
};
dojo.provide("dojo.lfx.html");
dojo.lfx.html._byId=function(_469){
if(!_469){
return [];
}
if(dojo.lang.isArrayLike(_469)){
if(!_469.alreadyChecked){
var n=[];
dojo.lang.forEach(_469,function(node){
n.push(dojo.byId(node));
});
n.alreadyChecked=true;
return n;
}else{
return _469;
}
}else{
var n=[];
n.push(dojo.byId(_469));
n.alreadyChecked=true;
return n;
}
};
dojo.lfx.html.propertyAnimation=function(_46c,_46d,_46e,_46f,_470){
_46c=dojo.lfx.html._byId(_46c);
var _471={"propertyMap":_46d,"nodes":_46c,"duration":_46e,"easing":_46f||dojo.lfx.easeDefault};
var _472=function(args){
if(args.nodes.length==1){
var pm=args.propertyMap;
if(!dojo.lang.isArray(args.propertyMap)){
var parr=[];
for(var _476 in pm){
pm[_476].property=_476;
parr.push(pm[_476]);
}
pm=args.propertyMap=parr;
}
dojo.lang.forEach(pm,function(prop){
if(dj_undef("start",prop)){
if(prop.property!="opacity"){
prop.start=parseInt(dojo.html.getComputedStyle(args.nodes[0],prop.property));
}else{
prop.start=dojo.html.getOpacity(args.nodes[0]);
}
}
});
}
};
var _478=function(_479){
var _47a=[];
dojo.lang.forEach(_479,function(c){
_47a.push(Math.round(c));
});
return _47a;
};
var _47c=function(n,_47e){
n=dojo.byId(n);
if(!n||!n.style){
return;
}
for(var s in _47e){
if(s=="opacity"){
dojo.html.setOpacity(n,_47e[s]);
}else{
n.style[s]=_47e[s];
}
}
};
var _480=function(_481){
this._properties=_481;
this.diffs=new Array(_481.length);
dojo.lang.forEach(_481,function(prop,i){
if(dojo.lang.isFunction(prop.start)){
prop.start=prop.start(prop,i);
}
if(dojo.lang.isFunction(prop.end)){
prop.end=prop.end(prop,i);
}
if(dojo.lang.isArray(prop.start)){
this.diffs[i]=null;
}else{
if(prop.start instanceof dojo.gfx.color.Color){
prop.startRgb=prop.start.toRgb();
prop.endRgb=prop.end.toRgb();
}else{
this.diffs[i]=prop.end-prop.start;
}
}
},this);
this.getValue=function(n){
var ret={};
dojo.lang.forEach(this._properties,function(prop,i){
var _488=null;
if(dojo.lang.isArray(prop.start)){
}else{
if(prop.start instanceof dojo.gfx.color.Color){
_488=(prop.units||"rgb")+"(";
for(var j=0;j<prop.startRgb.length;j++){
_488+=Math.round(((prop.endRgb[j]-prop.startRgb[j])*n)+prop.startRgb[j])+(j<prop.startRgb.length-1?",":"");
}
_488+=")";
}else{
_488=((this.diffs[i])*n)+prop.start+(prop.property!="opacity"?prop.units||"px":"");
}
}
ret[dojo.html.toCamelCase(prop.property)]=_488;
},this);
return ret;
};
};
var anim=new dojo.lfx.Animation({beforeBegin:function(){
_472(_471);
anim.curve=new _480(_471.propertyMap);
},onAnimate:function(_48b){
dojo.lang.forEach(_471.nodes,function(node){
_47c(node,_48b);
});
}},_471.duration,null,_471.easing);
if(_470){
for(var x in _470){
if(dojo.lang.isFunction(_470[x])){
anim.connect(x,anim,_470[x]);
}
}
}
return anim;
};
dojo.lfx.html._makeFadeable=function(_48e){
var _48f=function(node){
if(dojo.render.html.ie){
if((node.style.zoom.length==0)&&(dojo.html.getStyle(node,"zoom")=="normal")){
node.style.zoom="1";
}
if((node.style.width.length==0)&&(dojo.html.getStyle(node,"width")=="auto")){
node.style.width="auto";
}
}
};
if(dojo.lang.isArrayLike(_48e)){
dojo.lang.forEach(_48e,_48f);
}else{
_48f(_48e);
}
};
dojo.lfx.html.fade=function(_491,_492,_493,_494,_495){
_491=dojo.lfx.html._byId(_491);
var _496={property:"opacity"};
if(!dj_undef("start",_492)){
_496.start=_492.start;
}else{
_496.start=function(){
return dojo.html.getOpacity(_491[0]);
};
}
if(!dj_undef("end",_492)){
_496.end=_492.end;
}else{
dojo.raise("dojo.lfx.html.fade needs an end value");
}
var anim=dojo.lfx.propertyAnimation(_491,[_496],_493,_494);
anim.connect("beforeBegin",function(){
dojo.lfx.html._makeFadeable(_491);
});
if(_495){
anim.connect("onEnd",function(){
_495(_491,anim);
});
}
return anim;
};
dojo.lfx.html.fadeIn=function(_498,_499,_49a,_49b){
return dojo.lfx.html.fade(_498,{end:1},_499,_49a,_49b);
};
dojo.lfx.html.fadeOut=function(_49c,_49d,_49e,_49f){
return dojo.lfx.html.fade(_49c,{end:0},_49d,_49e,_49f);
};
dojo.lfx.html.fadeShow=function(_4a0,_4a1,_4a2,_4a3){
_4a0=dojo.lfx.html._byId(_4a0);
dojo.lang.forEach(_4a0,function(node){
dojo.html.setOpacity(node,0);
});
var anim=dojo.lfx.html.fadeIn(_4a0,_4a1,_4a2,_4a3);
anim.connect("beforeBegin",function(){
if(dojo.lang.isArrayLike(_4a0)){
dojo.lang.forEach(_4a0,dojo.html.show);
}else{
dojo.html.show(_4a0);
}
});
return anim;
};
dojo.lfx.html.fadeHide=function(_4a6,_4a7,_4a8,_4a9){
var anim=dojo.lfx.html.fadeOut(_4a6,_4a7,_4a8,function(){
if(dojo.lang.isArrayLike(_4a6)){
dojo.lang.forEach(_4a6,dojo.html.hide);
}else{
dojo.html.hide(_4a6);
}
if(_4a9){
_4a9(_4a6,anim);
}
});
return anim;
};
dojo.lfx.html.wipeIn=function(_4ab,_4ac,_4ad,_4ae){
_4ab=dojo.lfx.html._byId(_4ab);
var _4af=[];
dojo.lang.forEach(_4ab,function(node){
var _4b1={};
dojo.html.show(node);
var _4b2=dojo.html.getBorderBox(node).height;
dojo.html.hide(node);
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:1,end:function(){
return _4b2;
}}},_4ac,_4ad);
anim.connect("beforeBegin",function(){
_4b1.overflow=node.style.overflow;
_4b1.height=node.style.height;
with(node.style){
overflow="hidden";
_4b2="1px";
}
dojo.html.show(node);
});
anim.connect("onEnd",function(){
with(node.style){
overflow=_4b1.overflow;
_4b2=_4b1.height;
}
if(_4ae){
_4ae(node,anim);
}
});
_4af.push(anim);
});
return dojo.lfx.combine(_4af);
};
dojo.lfx.html.wipeOut=function(_4b4,_4b5,_4b6,_4b7){
_4b4=dojo.lfx.html._byId(_4b4);
var _4b8=[];
dojo.lang.forEach(_4b4,function(node){
var _4ba={};
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:function(){
return dojo.html.getContentBox(node).height;
},end:1}},_4b5,_4b6,{"beforeBegin":function(){
_4ba.overflow=node.style.overflow;
_4ba.height=node.style.height;
with(node.style){
overflow="hidden";
}
dojo.html.show(node);
},"onEnd":function(){
dojo.html.hide(node);
with(node.style){
overflow=_4ba.overflow;
height=_4ba.height;
}
if(_4b7){
_4b7(node,anim);
}
}});
_4b8.push(anim);
});
return dojo.lfx.combine(_4b8);
};
dojo.lfx.html.slideTo=function(_4bc,_4bd,_4be,_4bf,_4c0){
_4bc=dojo.lfx.html._byId(_4bc);
var _4c1=[];
var _4c2=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_4bd)){
dojo.deprecated("dojo.lfx.html.slideTo(node, array)","use dojo.lfx.html.slideTo(node, {top: value, left: value});","0.5");
_4bd={top:_4bd[0],left:_4bd[1]};
}
dojo.lang.forEach(_4bc,function(node){
var top=null;
var left=null;
var init=(function(){
var _4c7=node;
return function(){
var pos=_4c2(_4c7,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_4c2(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_4c2(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_4c7,true);
dojo.html.setStyleAttributes(_4c7,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:(_4bd.top||0)},"left":{start:left,end:(_4bd.left||0)}},_4be,_4bf,{"beforeBegin":init});
if(_4c0){
anim.connect("onEnd",function(){
_4c0(_4bc,anim);
});
}
_4c1.push(anim);
});
return dojo.lfx.combine(_4c1);
};
dojo.lfx.html.slideBy=function(_4cb,_4cc,_4cd,_4ce,_4cf){
_4cb=dojo.lfx.html._byId(_4cb);
var _4d0=[];
var _4d1=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_4cc)){
dojo.deprecated("dojo.lfx.html.slideBy(node, array)","use dojo.lfx.html.slideBy(node, {top: value, left: value});","0.5");
_4cc={top:_4cc[0],left:_4cc[1]};
}
dojo.lang.forEach(_4cb,function(node){
var top=null;
var left=null;
var init=(function(){
var _4d6=node;
return function(){
var pos=_4d1(_4d6,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_4d1(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_4d1(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_4d6,true);
dojo.html.setStyleAttributes(_4d6,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:top+(_4cc.top||0)},"left":{start:left,end:left+(_4cc.left||0)}},_4cd,_4ce).connect("beforeBegin",init);
if(_4cf){
anim.connect("onEnd",function(){
_4cf(_4cb,anim);
});
}
_4d0.push(anim);
});
return dojo.lfx.combine(_4d0);
};
dojo.lfx.html.explode=function(_4da,_4db,_4dc,_4dd,_4de){
var h=dojo.html;
_4da=dojo.byId(_4da);
_4db=dojo.byId(_4db);
var _4e0=h.toCoordinateObject(_4da,true);
var _4e1=document.createElement("div");
h.copyStyle(_4e1,_4db);
if(_4db.explodeClassName){
_4e1.className=_4db.explodeClassName;
}
with(_4e1.style){
position="absolute";
display="none";
}
dojo.body().appendChild(_4e1);
with(_4db.style){
visibility="hidden";
display="block";
}
var _4e2=h.toCoordinateObject(_4db,true);
with(_4db.style){
display="none";
visibility="visible";
}
var _4e3={opacity:{start:0.5,end:1}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_4e3[type]={start:_4e0[type],end:_4e2[type]};
});
var anim=new dojo.lfx.propertyAnimation(_4e1,_4e3,_4dc,_4dd,{"beforeBegin":function(){
h.setDisplay(_4e1,"block");
},"onEnd":function(){
h.setDisplay(_4db,"block");
_4e1.parentNode.removeChild(_4e1);
}});
if(_4de){
anim.connect("onEnd",function(){
_4de(_4db,anim);
});
}
return anim;
};
dojo.lfx.html.implode=function(_4e6,end,_4e8,_4e9,_4ea){
var h=dojo.html;
_4e6=dojo.byId(_4e6);
end=dojo.byId(end);
var _4ec=dojo.html.toCoordinateObject(_4e6,true);
var _4ed=dojo.html.toCoordinateObject(end,true);
var _4ee=document.createElement("div");
dojo.html.copyStyle(_4ee,_4e6);
if(_4e6.explodeClassName){
_4ee.className=_4e6.explodeClassName;
}
dojo.html.setOpacity(_4ee,0.3);
with(_4ee.style){
position="absolute";
display="none";
backgroundColor=h.getStyle(_4e6,"background-color").toLowerCase();
}
dojo.body().appendChild(_4ee);
var _4ef={opacity:{start:1,end:0.5}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_4ef[type]={start:_4ec[type],end:_4ed[type]};
});
var anim=new dojo.lfx.propertyAnimation(_4ee,_4ef,_4e8,_4e9,{"beforeBegin":function(){
dojo.html.hide(_4e6);
dojo.html.show(_4ee);
},"onEnd":function(){
_4ee.parentNode.removeChild(_4ee);
}});
if(_4ea){
anim.connect("onEnd",function(){
_4ea(_4e6,anim);
});
}
return anim;
};
dojo.lfx.html.highlight=function(_4f2,_4f3,_4f4,_4f5,_4f6){
_4f2=dojo.lfx.html._byId(_4f2);
var _4f7=[];
dojo.lang.forEach(_4f2,function(node){
var _4f9=dojo.html.getBackgroundColor(node);
var bg=dojo.html.getStyle(node,"background-color").toLowerCase();
var _4fb=dojo.html.getStyle(node,"background-image");
var _4fc=(bg=="transparent"||bg=="rgba(0, 0, 0, 0)");
while(_4f9.length>3){
_4f9.pop();
}
var rgb=new dojo.gfx.color.Color(_4f3);
var _4fe=new dojo.gfx.color.Color(_4f9);
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:rgb,end:_4fe}},_4f4,_4f5,{"beforeBegin":function(){
if(_4fb){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+rgb.toRgb().join(",")+")";
},"onEnd":function(){
if(_4fb){
node.style.backgroundImage=_4fb;
}
if(_4fc){
node.style.backgroundColor="transparent";
}
if(_4f6){
_4f6(node,anim);
}
}});
_4f7.push(anim);
});
return dojo.lfx.combine(_4f7);
};
dojo.lfx.html.unhighlight=function(_500,_501,_502,_503,_504){
_500=dojo.lfx.html._byId(_500);
var _505=[];
dojo.lang.forEach(_500,function(node){
var _507=new dojo.gfx.color.Color(dojo.html.getBackgroundColor(node));
var rgb=new dojo.gfx.color.Color(_501);
var _509=dojo.html.getStyle(node,"background-image");
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:_507,end:rgb}},_502,_503,{"beforeBegin":function(){
if(_509){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+_507.toRgb().join(",")+")";
},"onEnd":function(){
if(_504){
_504(node,anim);
}
}});
_505.push(anim);
});
return dojo.lfx.combine(_505);
};
dojo.lang.mixin(dojo.lfx,dojo.lfx.html);
dojo.provide("dojo.lfx.*");
dojo.provide("dojo.event.common");
dojo.event=new function(){
this._canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
function interpolateArgs(args,_50c){
var dl=dojo.lang;
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null,rate:0,adviceMsg:false};
switch(args.length){
case 0:
return;
case 1:
return;
case 2:
ao.srcFunc=args[0];
ao.adviceFunc=args[1];
break;
case 3:
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isFunction(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _50f=dl.nameAnonFunc(args[2],ao.adviceObj,_50c);
ao.adviceFunc=_50f;
}else{
if((dl.isFunction(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=dj_global;
var _50f=dl.nameAnonFunc(args[0],ao.srcObj,_50c);
ao.srcFunc=_50f;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((dl.isObject(args[0]))&&(dl.isObject(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isString(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isFunction(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
var _50f=dl.nameAnonFunc(args[1],dj_global,_50c);
ao.srcFunc=_50f;
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))&&(dl.isFunction(args[3]))){
ao.srcObj=args[1];
ao.srcFunc=args[2];
var _50f=dl.nameAnonFunc(args[3],dj_global,_50c);
ao.adviceObj=dj_global;
ao.adviceFunc=_50f;
}else{
if(dl.isObject(args[1])){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if(dl.isObject(args[2])){
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
ao.srcObj=ao.adviceObj=ao.aroundObj=dj_global;
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
ao.aroundFunc=args[3];
}
}
}
}
}
}
break;
case 6:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundFunc=args[5];
ao.aroundObj=dj_global;
break;
default:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundObj=args[5];
ao.aroundFunc=args[6];
ao.once=args[7];
ao.delay=args[8];
ao.rate=args[9];
ao.adviceMsg=args[10];
break;
}
if(dl.isFunction(ao.aroundFunc)){
var _50f=dl.nameAnonFunc(ao.aroundFunc,ao.aroundObj,_50c);
ao.aroundFunc=_50f;
}
if(dl.isFunction(ao.srcFunc)){
ao.srcFunc=dl.getNameInObj(ao.srcObj,ao.srcFunc);
}
if(dl.isFunction(ao.adviceFunc)){
ao.adviceFunc=dl.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&(dl.isFunction(ao.aroundFunc))){
ao.aroundFunc=dl.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
if(!ao.adviceFunc){
dojo.debug("bad adviceFunc for srcFunc: "+ao.srcFunc);
dojo.debugShallow(ao);
}
return ao;
}
this.connect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.connect(ao);
}
ao.srcFunc="onkeypress";
}
if(dojo.lang.isArray(ao.srcObj)&&ao.srcObj!=""){
var _511={};
for(var x in ao){
_511[x]=ao[x];
}
var mjps=[];
dojo.lang.forEach(ao.srcObj,function(src){
if((dojo.render.html.capable)&&(dojo.lang.isString(src))){
src=dojo.byId(src);
}
_511.srcObj=src;
mjps.push(dojo.event.connect.call(dojo.event,_511));
});
return mjps;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.log=function(a1,a2){
var _519;
if((arguments.length==1)&&(typeof a1=="object")){
_519=a1;
}else{
_519={srcObj:a1,srcFunc:a2};
}
_519.adviceFunc=function(){
var _51a=[];
for(var x=0;x<arguments.length;x++){
_51a.push(arguments[x]);
}
dojo.debug("("+_519.srcObj+")."+_519.srcFunc,":",_51a.join(", "));
};
this.kwConnect(_519);
};
this.connectBefore=function(){
var args=["before"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectAround=function(){
var args=["around"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectOnce=function(){
var ao=interpolateArgs(arguments,true);
ao.once=true;
return this.connect(ao);
};
this._kwConnectImpl=function(_521,_522){
var fn=(_522)?"disconnect":"connect";
if(typeof _521["srcFunc"]=="function"){
_521.srcObj=_521["srcObj"]||dj_global;
var _524=dojo.lang.nameAnonFunc(_521.srcFunc,_521.srcObj,true);
_521.srcFunc=_524;
}
if(typeof _521["adviceFunc"]=="function"){
_521.adviceObj=_521["adviceObj"]||dj_global;
var _524=dojo.lang.nameAnonFunc(_521.adviceFunc,_521.adviceObj,true);
_521.adviceFunc=_524;
}
_521.srcObj=_521["srcObj"]||dj_global;
_521.adviceObj=_521["adviceObj"]||_521["targetObj"]||dj_global;
_521.adviceFunc=_521["adviceFunc"]||_521["targetFunc"];
return dojo.event[fn](_521);
};
this.kwConnect=function(_525){
return this._kwConnectImpl(_525,false);
};
this.disconnect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(!ao.adviceFunc){
return;
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.disconnect(ao);
}
ao.srcFunc="onkeypress";
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_528){
return this._kwConnectImpl(_528,true);
};
};
dojo.event.MethodInvocation=function(_529,obj,args){
this.jp_=_529;
this.object=obj;
this.args=[];
for(var x=0;x<args.length;x++){
this.args[x]=args[x];
}
this.around_index=-1;
};
dojo.event.MethodInvocation.prototype.proceed=function(){
this.around_index++;
if(this.around_index>=this.jp_.around.length){
return this.jp_.object[this.jp_.methodname].apply(this.jp_.object,this.args);
}else{
var ti=this.jp_.around[this.around_index];
var mobj=ti[0]||dj_global;
var meth=ti[1];
return mobj[meth].call(mobj,this);
}
};
dojo.event.MethodJoinPoint=function(obj,_531){
this.object=obj||dj_global;
this.methodname=_531;
this.methodfunc=this.object[_531];
this.squelch=false;
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_533){
if(!obj){
obj=dj_global;
}
if(!obj[_533]){
obj[_533]=function(){
};
if(!obj[_533]){
dojo.raise("Cannot set do-nothing method on that object "+_533);
}
}else{
if((!dojo.lang.isFunction(obj[_533]))&&(!dojo.lang.isAlien(obj[_533]))){
return null;
}
}
var _534=_533+"$joinpoint";
var _535=_533+"$joinpoint$method";
var _536=obj[_534];
if(!_536){
var _537=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_537=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_534,_535,_533]);
}
}
var _538=obj[_533].length;
obj[_535]=obj[_533];
_536=obj[_534]=new dojo.event.MethodJoinPoint(obj,_535);
obj[_533]=function(){
var args=[];
if((_537)&&(!arguments.length)){
var evt=null;
try{
if(obj.ownerDocument){
evt=obj.ownerDocument.parentWindow.event;
}else{
if(obj.documentElement){
evt=obj.documentElement.ownerDocument.parentWindow.event;
}else{
if(obj.event){
evt=obj.event;
}else{
evt=window.event;
}
}
}
}
catch(e){
evt=window.event;
}
if(evt){
args.push(dojo.event.browser.fixEvent(evt,this));
}
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_537)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x],this));
}else{
args.push(arguments[x]);
}
}
}
return _536.run.apply(_536,args);
};
obj[_533].__preJoinArity=_538;
}
return _536;
};
dojo.lang.extend(dojo.event.MethodJoinPoint,{unintercept:function(){
this.object[this.methodname]=this.methodfunc;
this.before=[];
this.after=[];
this.around=[];
},disconnect:dojo.lang.forward("unintercept"),run:function(){
var obj=this.object||dj_global;
var args=arguments;
var _53e=[];
for(var x=0;x<args.length;x++){
_53e[x]=args[x];
}
var _540=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _542=marr[0]||dj_global;
var _543=marr[1];
if(!_542[_543]){
dojo.raise("function \""+_543+"\" does not exist on \""+_542+"\"");
}
var _544=marr[2]||dj_global;
var _545=marr[3];
var msg=marr[6];
var _547;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _542[_543].apply(_542,to.args);
}};
to.args=_53e;
var _549=parseInt(marr[4]);
var _54a=((!isNaN(_549))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _54d=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event._canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_540(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_545){
_544[_545].call(_544,to);
}else{
if((_54a)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_542[_543].call(_542,to);
}else{
_542[_543].apply(_542,args);
}
},_549);
}else{
if(msg){
_542[_543].call(_542,to);
}else{
_542[_543].apply(_542,args);
}
}
}
};
var _550=function(){
if(this.squelch){
try{
return _540.apply(this,arguments);
}
catch(e){
dojo.debug(e);
}
}else{
return _540.apply(this,arguments);
}
};
if((this["before"])&&(this.before.length>0)){
dojo.lang.forEach(this.before.concat(new Array()),_550);
}
var _551;
try{
if((this["around"])&&(this.around.length>0)){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_551=mi.proceed();
}else{
if(this.methodfunc){
_551=this.object[this.methodname].apply(this.object,args);
}
}
}
catch(e){
if(!this.squelch){
dojo.raise(e);
}
}
if((this["after"])&&(this.after.length>0)){
dojo.lang.forEach(this.after.concat(new Array()),_550);
}
return (this.methodfunc)?_551:null;
},getArr:function(kind){
var type="after";
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
type="before";
}else{
if(kind=="around"){
type="around";
}
}
if(!this[type]){
this[type]=[];
}
return this[type];
},kwAddAdvice:function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
},addAdvice:function(_556,_557,_558,_559,_55a,_55b,once,_55d,rate,_55f){
var arr=this.getArr(_55a);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_556,_557,_558,_559,_55d,rate,_55f];
if(once){
if(this.hasAdvice(_556,_557,_55a,arr)>=0){
return;
}
}
if(_55b=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
},hasAdvice:function(_562,_563,_564,arr){
if(!arr){
arr=this.getArr(_564);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
var aao=(typeof _563=="object")?(new String(_563)).toString():_563;
var a1o=(typeof arr[x][1]=="object")?(new String(arr[x][1])).toString():arr[x][1];
if((arr[x][0]==_562)&&(a1o==aao)){
ind=x;
}
}
return ind;
},removeAdvice:function(_56a,_56b,_56c,once){
var arr=this.getArr(_56c);
var ind=this.hasAdvice(_56a,_56b,_56c,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_56a,_56b,_56c,arr);
}
return true;
}});
dojo.provide("dojo.event.topic");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_570){
if(!this.topics[_570]){
this.topics[_570]=new this.TopicImpl(_570);
}
return this.topics[_570];
};
this.registerPublisher=function(_571,obj,_573){
var _571=this.getTopic(_571);
_571.registerPublisher(obj,_573);
};
this.subscribe=function(_574,obj,_576){
var _574=this.getTopic(_574);
_574.subscribe(obj,_576);
};
this.unsubscribe=function(_577,obj,_579){
var _577=this.getTopic(_577);
_577.unsubscribe(obj,_579);
};
this.destroy=function(_57a){
this.getTopic(_57a).destroy();
delete this.topics[_57a];
};
this.publishApply=function(_57b,args){
var _57b=this.getTopic(_57b);
_57b.sendMessage.apply(_57b,args);
};
this.publish=function(_57d,_57e){
var _57d=this.getTopic(_57d);
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
_57d.sendMessage.apply(_57d,args);
};
};
dojo.event.topic.TopicImpl=function(_581){
this.topicName=_581;
this.subscribe=function(_582,_583){
var tf=_583||_582;
var to=(!_583)?dj_global:_582;
return dojo.event.kwConnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this.unsubscribe=function(_586,_587){
var tf=(!_587)?_586:_587;
var to=(!_587)?null:_586;
return dojo.event.kwDisconnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this._getJoinPoint=function(){
return dojo.event.MethodJoinPoint.getForMethod(this,"sendMessage");
};
this.setSquelch=function(_58a){
this._getJoinPoint().squelch=_58a;
};
this.destroy=function(){
this._getJoinPoint().disconnect();
};
this.registerPublisher=function(_58b,_58c){
dojo.event.connect(_58b,_58c,this,"sendMessage");
};
this.sendMessage=function(_58d){
};
};
dojo.provide("dojo.event.browser");
dojo._ie_clobber=new function(){
this.clobberNodes=[];
function nukeProp(node,prop){
try{
node[prop]=null;
}
catch(e){
}
try{
delete node[prop];
}
catch(e){
}
try{
node.removeAttribute(prop);
}
catch(e){
}
}
this.clobber=function(_590){
var na;
var tna;
if(_590){
tna=_590.all||_590.getElementsByTagName("*");
na=[_590];
for(var x=0;x<tna.length;x++){
if(tna[x]["__doClobber__"]){
na.push(tna[x]);
}
}
}else{
try{
window.onload=null;
}
catch(e){
}
na=(this.clobberNodes.length)?this.clobberNodes:document.all;
}
tna=null;
var _594={};
for(var i=na.length-1;i>=0;i=i-1){
var el=na[i];
try{
if(el&&el["__clobberAttrs__"]){
for(var j=0;j<el.__clobberAttrs__.length;j++){
nukeProp(el,el.__clobberAttrs__[j]);
}
nukeProp(el,"__clobberAttrs__");
nukeProp(el,"__doClobber__");
}
}
catch(e){
}
}
na=null;
};
};
if(dojo.render.html.ie){
dojo.addOnUnload(function(){
dojo._ie_clobber.clobber();
try{
if((dojo["widget"])&&(dojo.widget["manager"])){
dojo.widget.manager.destroyAll();
}
}
catch(e){
}
try{
window.onload=null;
}
catch(e){
}
try{
window.onunload=null;
}
catch(e){
}
dojo._ie_clobber.clobberNodes=[];
});
}
dojo.event.browser=new function(){
var _598=0;
this.normalizedEventName=function(_599){
switch(_599){
case "CheckboxStateChange":
case "DOMAttrModified":
case "DOMMenuItemActive":
case "DOMMenuItemInactive":
case "DOMMouseScroll":
case "DOMNodeInserted":
case "DOMNodeRemoved":
case "RadioStateChange":
return _599;
break;
default:
return _599.toLowerCase();
break;
}
};
this.clean=function(node){
if(dojo.render.html.ie){
dojo._ie_clobber.clobber(node);
}
};
this.addClobberNode=function(node){
if(!dojo.render.html.ie){
return;
}
if(!node["__doClobber__"]){
node.__doClobber__=true;
dojo._ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
};
this.addClobberNodeAttrs=function(node,_59d){
if(!dojo.render.html.ie){
return;
}
this.addClobberNode(node);
for(var x=0;x<_59d.length;x++){
node.__clobberAttrs__.push(_59d[x]);
}
};
this.removeListener=function(node,_5a0,fp,_5a2){
if(!_5a2){
var _5a2=false;
}
_5a0=dojo.event.browser.normalizedEventName(_5a0);
if((_5a0=="onkey")||(_5a0=="key")){
if(dojo.render.html.ie){
this.removeListener(node,"onkeydown",fp,_5a2);
}
_5a0="onkeypress";
}
if(_5a0.substr(0,2)=="on"){
_5a0=_5a0.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_5a0,fp,_5a2);
}
};
this.addListener=function(node,_5a4,fp,_5a6,_5a7){
if(!node){
return;
}
if(!_5a6){
var _5a6=false;
}
_5a4=dojo.event.browser.normalizedEventName(_5a4);
if((_5a4=="onkey")||(_5a4=="key")){
if(dojo.render.html.ie){
this.addListener(node,"onkeydown",fp,_5a6,_5a7);
}
_5a4="onkeypress";
}
if(_5a4.substr(0,2)!="on"){
_5a4="on"+_5a4;
}
if(!_5a7){
var _5a8=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt,this));
if(_5a6){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
}else{
_5a8=fp;
}
if(node.addEventListener){
node.addEventListener(_5a4.substr(2),_5a8,_5a6);
return _5a8;
}else{
if(typeof node[_5a4]=="function"){
var _5ab=node[_5a4];
node[_5a4]=function(e){
_5ab(e);
return _5a8(e);
};
}else{
node[_5a4]=_5a8;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_5a4]);
}
return _5a8;
}
};
this.isEvent=function(obj){
return (typeof obj!="undefined")&&(typeof Event!="undefined")&&(obj.eventPhase);
};
this.currentEvent=null;
this.callListener=function(_5ae,_5af){
if(typeof _5ae!="function"){
dojo.raise("listener not a function: "+_5ae);
}
dojo.event.browser.currentEvent.currentTarget=_5af;
return _5ae.call(_5af,dojo.event.browser.currentEvent);
};
this._stopPropagation=function(){
dojo.event.browser.currentEvent.cancelBubble=true;
};
this._preventDefault=function(){
dojo.event.browser.currentEvent.returnValue=false;
};
this.keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_CLEAR:12,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_HELP:47,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_NUMPAD_0:96,KEY_NUMPAD_1:97,KEY_NUMPAD_2:98,KEY_NUMPAD_3:99,KEY_NUMPAD_4:100,KEY_NUMPAD_5:101,KEY_NUMPAD_6:102,KEY_NUMPAD_7:103,KEY_NUMPAD_8:104,KEY_NUMPAD_9:105,KEY_NUMPAD_MULTIPLY:106,KEY_NUMPAD_PLUS:107,KEY_NUMPAD_ENTER:108,KEY_NUMPAD_MINUS:109,KEY_NUMPAD_PERIOD:110,KEY_NUMPAD_DIVIDE:111,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_F13:124,KEY_F14:125,KEY_F15:126,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
this.revKeys=[];
for(var key in this.keys){
this.revKeys[this.keys[key]]=key;
}
this.fixEvent=function(evt,_5b2){
if(!evt){
if(window["event"]){
evt=window.event;
}
}
if((evt["type"])&&(evt["type"].indexOf("key")==0)){
evt.keys=this.revKeys;
for(var key in this.keys){
evt[key]=this.keys[key];
}
if(evt["type"]=="keydown"&&dojo.render.html.ie){
switch(evt.keyCode){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_LEFT_WINDOW:
case evt.KEY_RIGHT_WINDOW:
case evt.KEY_SELECT:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
case evt.KEY_NUMPAD_0:
case evt.KEY_NUMPAD_1:
case evt.KEY_NUMPAD_2:
case evt.KEY_NUMPAD_3:
case evt.KEY_NUMPAD_4:
case evt.KEY_NUMPAD_5:
case evt.KEY_NUMPAD_6:
case evt.KEY_NUMPAD_7:
case evt.KEY_NUMPAD_8:
case evt.KEY_NUMPAD_9:
case evt.KEY_NUMPAD_PERIOD:
break;
case evt.KEY_NUMPAD_MULTIPLY:
case evt.KEY_NUMPAD_PLUS:
case evt.KEY_NUMPAD_ENTER:
case evt.KEY_NUMPAD_MINUS:
case evt.KEY_NUMPAD_DIVIDE:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
case evt.KEY_PAGE_UP:
case evt.KEY_PAGE_DOWN:
case evt.KEY_END:
case evt.KEY_HOME:
case evt.KEY_LEFT_ARROW:
case evt.KEY_UP_ARROW:
case evt.KEY_RIGHT_ARROW:
case evt.KEY_DOWN_ARROW:
case evt.KEY_INSERT:
case evt.KEY_DELETE:
case evt.KEY_F1:
case evt.KEY_F2:
case evt.KEY_F3:
case evt.KEY_F4:
case evt.KEY_F5:
case evt.KEY_F6:
case evt.KEY_F7:
case evt.KEY_F8:
case evt.KEY_F9:
case evt.KEY_F10:
case evt.KEY_F11:
case evt.KEY_F12:
case evt.KEY_F12:
case evt.KEY_F13:
case evt.KEY_F14:
case evt.KEY_F15:
case evt.KEY_CLEAR:
case evt.KEY_HELP:
evt.key=evt.keyCode;
break;
default:
if(evt.ctrlKey||evt.altKey){
var _5b4=evt.keyCode;
if(_5b4>=65&&_5b4<=90&&evt.shiftKey==false){
_5b4+=32;
}
if(_5b4>=1&&_5b4<=26&&evt.ctrlKey){
_5b4+=96;
}
evt.key=String.fromCharCode(_5b4);
}
}
}else{
if(evt["type"]=="keypress"){
if(dojo.render.html.opera){
if(evt.which==0){
evt.key=evt.keyCode;
}else{
if(evt.which>0){
switch(evt.which){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
evt.key=evt.which;
break;
default:
var _5b4=evt.which;
if((evt.ctrlKey||evt.altKey||evt.metaKey)&&(evt.which>=65&&evt.which<=90&&evt.shiftKey==false)){
_5b4+=32;
}
evt.key=String.fromCharCode(_5b4);
}
}
}
}else{
if(dojo.render.html.ie){
if(!evt.ctrlKey&&!evt.altKey&&evt.keyCode>=evt.KEY_SPACE){
evt.key=String.fromCharCode(evt.keyCode);
}
}else{
if(dojo.render.html.safari){
switch(evt.keyCode){
case 63232:
evt.key=evt.KEY_UP_ARROW;
break;
case 63233:
evt.key=evt.KEY_DOWN_ARROW;
break;
case 63234:
evt.key=evt.KEY_LEFT_ARROW;
break;
case 63235:
evt.key=evt.KEY_RIGHT_ARROW;
break;
default:
evt.key=evt.charCode>0?String.fromCharCode(evt.charCode):evt.keyCode;
}
}else{
evt.key=evt.charCode>0?String.fromCharCode(evt.charCode):evt.keyCode;
}
}
}
}
}
}
if(dojo.render.html.ie){
if(!evt.target){
evt.target=evt.srcElement;
}
if(!evt.currentTarget){
evt.currentTarget=(_5b2?_5b2:evt.srcElement);
}
if(!evt.layerX){
evt.layerX=evt.offsetX;
}
if(!evt.layerY){
evt.layerY=evt.offsetY;
}
var doc=(evt.srcElement&&evt.srcElement.ownerDocument)?evt.srcElement.ownerDocument:document;
var _5b6=((dojo.render.html.ie55)||(doc["compatMode"]=="BackCompat"))?doc.body:doc.documentElement;
if(!evt.pageX){
evt.pageX=evt.clientX+(_5b6.scrollLeft||0);
}
if(!evt.pageY){
evt.pageY=evt.clientY+(_5b6.scrollTop||0);
}
if(evt.type=="mouseover"){
evt.relatedTarget=evt.fromElement;
}
if(evt.type=="mouseout"){
evt.relatedTarget=evt.toElement;
}
this.currentEvent=evt;
evt.callListener=this.callListener;
evt.stopPropagation=this._stopPropagation;
evt.preventDefault=this._preventDefault;
}
return evt;
};
this.stopEvent=function(evt){
if(window.event){
evt.returnValue=false;
evt.cancelBubble=true;
}else{
evt.preventDefault();
evt.stopPropagation();
}
};
};
dojo.provide("dojo.event.*");
dojo.provide("dojo.logging.Logger");
dojo.logging.Record=function(lvl,msg){
this.level=lvl;
this.message="";
this.msgArgs=[];
this.time=new Date();
if(dojo.lang.isArray(msg)){
if(msg.length>0&&dojo.lang.isString(msg[0])){
this.message=msg.shift();
}
this.msgArgs=msg;
}else{
this.message=msg;
}
};
dojo.logging.LogFilter=function(_5ba){
this.passChain=_5ba||"";
this.filter=function(_5bb){
return true;
};
};
dojo.logging.Logger=function(){
this.cutOffLevel=0;
this.propagate=true;
this.parent=null;
this.data=[];
this.filters=[];
this.handlers=[];
};
dojo.extend(dojo.logging.Logger,{argsToArr:function(args){
var ret=[];
for(var x=0;x<args.length;x++){
ret.push(args[x]);
}
return ret;
},setLevel:function(lvl){
this.cutOffLevel=parseInt(lvl);
},isEnabledFor:function(lvl){
return parseInt(lvl)>=this.cutOffLevel;
},getEffectiveLevel:function(){
if((this.cutOffLevel==0)&&(this.parent)){
return this.parent.getEffectiveLevel();
}
return this.cutOffLevel;
},addFilter:function(flt){
this.filters.push(flt);
return this.filters.length-1;
},removeFilterByIndex:function(_5c2){
if(this.filters[_5c2]){
delete this.filters[_5c2];
return true;
}
return false;
},removeFilter:function(_5c3){
for(var x=0;x<this.filters.length;x++){
if(this.filters[x]===_5c3){
delete this.filters[x];
return true;
}
}
return false;
},removeAllFilters:function(){
this.filters=[];
},filter:function(rec){
for(var x=0;x<this.filters.length;x++){
if((this.filters[x]["filter"])&&(!this.filters[x].filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
}
return true;
},addHandler:function(hdlr){
this.handlers.push(hdlr);
return this.handlers.length-1;
},handle:function(rec){
if((!this.filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
for(var x=0;x<this.handlers.length;x++){
if(this.handlers[x]["handle"]){
this.handlers[x].handle(rec);
}
}
return true;
},log:function(lvl,msg){
if((this.propagate)&&(this.parent)&&(this.parent.rec.level>=this.cutOffLevel)){
this.parent.log(lvl,msg);
return false;
}
this.handle(new dojo.logging.Record(lvl,msg));
return true;
},debug:function(msg){
return this.logType("DEBUG",this.argsToArr(arguments));
},info:function(msg){
return this.logType("INFO",this.argsToArr(arguments));
},warning:function(msg){
return this.logType("WARNING",this.argsToArr(arguments));
},error:function(msg){
return this.logType("ERROR",this.argsToArr(arguments));
},critical:function(msg){
return this.logType("CRITICAL",this.argsToArr(arguments));
},exception:function(msg,e,_5d3){
if(e){
var _5d4=[e.name,(e.description||e.message)];
if(e.fileName){
_5d4.push(e.fileName);
_5d4.push("line "+e.lineNumber);
}
msg+=" "+_5d4.join(" : ");
}
this.logType("ERROR",msg);
if(!_5d3){
throw e;
}
},logType:function(type,args){
return this.log.apply(this,[dojo.logging.log.getLevel(type),args]);
},warn:function(){
this.warning.apply(this,arguments);
},err:function(){
this.error.apply(this,arguments);
},crit:function(){
this.critical.apply(this,arguments);
}});
dojo.logging.LogHandler=function(_5d7){
this.cutOffLevel=(_5d7)?_5d7:0;
this.formatter=null;
this.data=[];
this.filters=[];
};
dojo.lang.extend(dojo.logging.LogHandler,{setFormatter:function(_5d8){
dojo.unimplemented("setFormatter");
},flush:function(){
},close:function(){
},handleError:function(){
},handle:function(_5d9){
if((this.filter(_5d9))&&(_5d9.level>=this.cutOffLevel)){
this.emit(_5d9);
}
},emit:function(_5da){
dojo.unimplemented("emit");
}});
void (function(){
var _5db=["setLevel","addFilter","removeFilterByIndex","removeFilter","removeAllFilters","filter"];
var tgt=dojo.logging.LogHandler.prototype;
var src=dojo.logging.Logger.prototype;
for(var x=0;x<_5db.length;x++){
tgt[_5db[x]]=src[_5db[x]];
}
})();
dojo.logging.log=new dojo.logging.Logger();
dojo.logging.log.levels=[{"name":"DEBUG","level":1},{"name":"INFO","level":2},{"name":"WARNING","level":3},{"name":"ERROR","level":4},{"name":"CRITICAL","level":5}];
dojo.logging.log.loggers={};
dojo.logging.log.getLogger=function(name){
if(!this.loggers[name]){
this.loggers[name]=new dojo.logging.Logger();
this.loggers[name].parent=this;
}
return this.loggers[name];
};
dojo.logging.log.getLevelName=function(lvl){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].level==lvl){
return this.levels[x].name;
}
}
return null;
};
dojo.logging.log.addLevelName=function(name,lvl){
if(this.getLevelName(name)){
this.err("could not add log level "+name+" because a level with that name already exists");
return false;
}
this.levels.append({"name":name,"level":parseInt(lvl)});
return true;
};
dojo.logging.log.getLevel=function(name){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].name.toUpperCase()==name.toUpperCase()){
return this.levels[x].level;
}
}
return null;
};
dojo.logging.MemoryLogHandler=function(_5e6,_5e7,_5e8,_5e9){
dojo.logging.LogHandler.call(this,_5e6);
this.numRecords=(typeof djConfig["loggingNumRecords"]!="undefined")?djConfig["loggingNumRecords"]:((_5e7)?_5e7:-1);
this.postType=(typeof djConfig["loggingPostType"]!="undefined")?djConfig["loggingPostType"]:(_5e8||-1);
this.postInterval=(typeof djConfig["loggingPostInterval"]!="undefined")?djConfig["loggingPostInterval"]:(_5e8||-1);
};
dojo.lang.inherits(dojo.logging.MemoryLogHandler,dojo.logging.LogHandler);
dojo.lang.extend(dojo.logging.MemoryLogHandler,{emit:function(_5ea){
if(!djConfig.isDebug){
return;
}
var _5eb=String(dojo.log.getLevelName(_5ea.level)+": "+_5ea.time.toLocaleTimeString())+": "+_5ea.message;
if(!dj_undef("println",dojo.hostenv)){
dojo.hostenv.println(_5eb);
}
this.data.push(_5ea);
if(this.numRecords!=-1){
while(this.data.length>this.numRecords){
this.data.shift();
}
}
}});
dojo.logging.logQueueHandler=new dojo.logging.MemoryLogHandler(0,50,0,10000);
dojo.logging.log.addHandler(dojo.logging.logQueueHandler);
dojo.log=dojo.logging.log;
dojo.provide("dojo.logging.*");
dojo.provide("dojo.string");
dojo.provide("dojo.io.common");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error","timeout"];
dojo.io.Request=function(url,_5ed,_5ee,_5ef){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_5ed){
this.mimetype=_5ed;
}
if(_5ee){
this.transport=_5ee;
}
if(arguments.length>=4){
this.changeUrl=_5ef;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,_5f2,_5f3){
},error:function(type,_5f5,_5f6,_5f7){
},timeout:function(type,_5f9,_5fa,_5fb){
},handle:function(type,data,_5fe,_5ff){
},timeoutSeconds:0,abort:function(){
},fromKwArgs:function(_600){
if(_600["url"]){
_600.url=_600.url.toString();
}
if(_600["formNode"]){
_600.formNode=dojo.byId(_600.formNode);
}
if(!_600["method"]&&_600["formNode"]&&_600["formNode"].method){
_600.method=_600["formNode"].method;
}
if(!_600["handle"]&&_600["handler"]){
_600.handle=_600.handler;
}
if(!_600["load"]&&_600["loaded"]){
_600.load=_600.loaded;
}
if(!_600["changeUrl"]&&_600["changeURL"]){
_600.changeUrl=_600.changeURL;
}
_600.encoding=dojo.lang.firstValued(_600["encoding"],djConfig["bindEncoding"],"");
_600.sendTransport=dojo.lang.firstValued(_600["sendTransport"],djConfig["ioSendTransport"],false);
var _601=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_600[fn]&&_601(_600[fn])){
continue;
}
if(_600["handle"]&&_601(_600["handle"])){
_600[fn]=_600.handle;
}
}
dojo.lang.mixin(this,_600);
}});
dojo.io.Error=function(msg,type,num){
this.message=msg;
this.type=type||"unknown";
this.number=num||0;
};
dojo.io.transports.addTransport=function(name){
this.push(name);
this[name]=dojo.io[name];
};
dojo.io.bind=function(_608){
if(!(_608 instanceof dojo.io.Request)){
try{
_608=new dojo.io.Request(_608);
}
catch(e){
dojo.debug(e);
}
}
var _609="";
if(_608["transport"]){
_609=_608["transport"];
if(!this[_609]){
dojo.io.sendBindError(_608,"No dojo.io.bind() transport with name '"+_608["transport"]+"'.");
return _608;
}
if(!this[_609].canHandle(_608)){
dojo.io.sendBindError(_608,"dojo.io.bind() transport with name '"+_608["transport"]+"' cannot handle this type of request.");
return _608;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_608))){
_609=tmp;
break;
}
}
if(_609==""){
dojo.io.sendBindError(_608,"None of the loaded transports for dojo.io.bind()"+" can handle the request.");
return _608;
}
}
this[_609].bind(_608);
_608.bindSuccess=true;
return _608;
};
dojo.io.sendBindError=function(_60c,_60d){
if((typeof _60c.error=="function"||typeof _60c.handle=="function")&&(typeof setTimeout=="function"||typeof setTimeout=="object")){
var _60e=new dojo.io.Error(_60d);
setTimeout(function(){
_60c[(typeof _60c.error=="function")?"error":"handle"]("error",_60e,null,_60c);
},50);
}else{
dojo.raise(_60d);
}
};
dojo.io.queueBind=function(_60f){
if(!(_60f instanceof dojo.io.Request)){
try{
_60f=new dojo.io.Request(_60f);
}
catch(e){
dojo.debug(e);
}
}
var _610=_60f.load;
_60f.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_610.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _612=_60f.error;
_60f.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_612.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_60f);
dojo.io._dispatchNextQueueBind();
return _60f;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
if(dojo.io._bindQueue.length>0){
dojo.io.bind(dojo.io._bindQueue.shift());
}else{
dojo.io._queueBindInFlight=false;
}
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_615,last){
var enc=/utf/i.test(_615||"")?encodeURIComponent:dojo.string.encodeAscii;
var _618=[];
var _619=new Object();
for(var name in map){
var _61b=function(elt){
var val=enc(name)+"="+enc(elt);
_618[(last==name)?"push":"unshift"](val);
};
if(!_619[name]){
var _61e=map[name];
if(dojo.lang.isArray(_61e)){
dojo.lang.forEach(_61e,_61b);
}else{
_61b(_61e);
}
}
}
return _618.join("&");
};
dojo.io.setIFrameSrc=function(_61f,src,_621){
try{
var r=dojo.render.html;
if(!_621){
if(r.safari){
_61f.location=src;
}else{
frames[_61f.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_61f.contentWindow.document;
}else{
if(r.safari){
idoc=_61f.document;
}else{
idoc=_61f.contentWindow;
}
}
if(!idoc){
_61f.location=src;
return;
}else{
idoc.location.replace(src);
}
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.provide("dojo.undo.browser");
try{
if((!djConfig["preventBackButtonFix"])&&(!dojo.hostenv.post_load_)){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
}
catch(e){
}
if(dojo.render.html.opera){
dojo.debug("Opera is not supported with dojo.undo.browser, so back/forward detection will not work.");
}
dojo.undo.browser={initialHref:window.location.href,initialHash:window.location.hash,moveForward:false,historyStack:[],forwardStack:[],historyIframe:null,bookmarkAnchor:null,locationTimer:null,setInitialState:function(args){
this.initialState=this._createState(this.initialHref,args,this.initialHash);
},addToHistory:function(args){
this.forwardStack=[];
var hash=null;
var url=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
dojo.body().appendChild(this.bookmarkAnchor);
this.bookmarkAnchor.style.display="none";
}
if(args["changeUrl"]){
hash="#"+((args["changeUrl"]!==true)?args["changeUrl"]:(new Date()).getTime());
if(this.historyStack.length==0&&this.initialState.urlHash==hash){
this.initialState=this._createState(url,args,hash);
return;
}else{
if(this.historyStack.length>0&&this.historyStack[this.historyStack.length-1].urlHash==hash){
this.historyStack[this.historyStack.length-1]=this._createState(url,args,hash);
return;
}
}
this.changingUrl=true;
setTimeout("window.location.href = '"+hash+"'; dojo.undo.browser.changingUrl = false;",1);
this.bookmarkAnchor.href=hash;
if(dojo.render.html.ie){
url=this._loadIframeHistory();
var _628=args["back"]||args["backButton"]||args["handle"];
var tcb=function(_62a){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+hash+"';",1);
}
_628.apply(this,[_62a]);
};
if(args["back"]){
args.back=tcb;
}else{
if(args["backButton"]){
args.backButton=tcb;
}else{
if(args["handle"]){
args.handle=tcb;
}
}
}
var _62b=args["forward"]||args["forwardButton"]||args["handle"];
var tfw=function(_62d){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_62b){
_62b.apply(this,[_62d]);
}
};
if(args["forward"]){
args.forward=tfw;
}else{
if(args["forwardButton"]){
args.forwardButton=tfw;
}else{
if(args["handle"]){
args.handle=tfw;
}
}
}
}else{
if(dojo.render.html.moz){
if(!this.locationTimer){
this.locationTimer=setInterval("dojo.undo.browser.checkLocation();",200);
}
}
}
}else{
url=this._loadIframeHistory();
}
this.historyStack.push(this._createState(url,args,hash));
},checkLocation:function(){
if(!this.changingUrl){
var hsl=this.historyStack.length;
if((window.location.hash==this.initialHash||window.location.href==this.initialHref)&&(hsl==1)){
this.handleBackButton();
return;
}
if(this.forwardStack.length>0){
if(this.forwardStack[this.forwardStack.length-1].urlHash==window.location.hash){
this.handleForwardButton();
return;
}
}
if((hsl>=2)&&(this.historyStack[hsl-2])){
if(this.historyStack[hsl-2].urlHash==window.location.hash){
this.handleBackButton();
return;
}
}
}
},iframeLoaded:function(evt,_630){
if(!dojo.render.html.opera){
var _631=this._getUrlQuery(_630.href);
if(_631==null){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
if(this.moveForward){
this.moveForward=false;
return;
}
if(this.historyStack.length>=2&&_631==this._getUrlQuery(this.historyStack[this.historyStack.length-2].url)){
this.handleBackButton();
}else{
if(this.forwardStack.length>0&&_631==this._getUrlQuery(this.forwardStack[this.forwardStack.length-1].url)){
this.handleForwardButton();
}
}
}
},handleBackButton:function(){
var _632=this.historyStack.pop();
if(!_632){
return;
}
var last=this.historyStack[this.historyStack.length-1];
if(!last&&this.historyStack.length==0){
last=this.initialState;
}
if(last){
if(last.kwArgs["back"]){
last.kwArgs["back"]();
}else{
if(last.kwArgs["backButton"]){
last.kwArgs["backButton"]();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("back");
}
}
}
}
this.forwardStack.push(_632);
},handleForwardButton:function(){
var last=this.forwardStack.pop();
if(!last){
return;
}
if(last.kwArgs["forward"]){
last.kwArgs.forward();
}else{
if(last.kwArgs["forwardButton"]){
last.kwArgs.forwardButton();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("forward");
}
}
}
this.historyStack.push(last);
},_createState:function(url,args,hash){
return {"url":url,"kwArgs":args,"urlHash":hash};
},_getUrlQuery:function(url){
var _639=url.split("?");
if(_639.length<2){
return null;
}else{
return _639[1];
}
},_loadIframeHistory:function(){
var url=dojo.hostenv.getBaseScriptUri()+"iframe_history.html?"+(new Date()).getTime();
this.moveForward=true;
dojo.io.setIFrameSrc(this.historyIframe,url,false);
return url;
}};
dojo.provide("dojo.io.BrowserIO");
dojo.io.checkChildrenForFile=function(node){
var _63c=false;
var _63d=node.getElementsByTagName("input");
dojo.lang.forEach(_63d,function(_63e){
if(_63c){
return;
}
if(_63e.getAttribute("type")=="file"){
_63c=true;
}
});
return _63c;
};
dojo.io.formHasFile=function(_63f){
return dojo.io.checkChildrenForFile(_63f);
};
dojo.io.updateNode=function(node,_641){
node=dojo.byId(node);
var args=_641;
if(dojo.lang.isString(_641)){
args={url:_641};
}
args.mimetype="text/html";
args.load=function(t,d,e){
while(node.firstChild){
if(dojo["event"]){
try{
dojo.event.browser.clean(node.firstChild);
}
catch(e){
}
}
node.removeChild(node.firstChild);
}
node.innerHTML=d;
};
dojo.io.bind(args);
};
dojo.io.formFilter=function(node){
var type=(node.type||"").toLowerCase();
return !node.disabled&&node.name&&!dojo.lang.inArray(["file","submit","image","reset","button"],type);
};
dojo.io.encodeForm=function(_648,_649,_64a){
if((!_648)||(!_648.tagName)||(!_648.tagName.toLowerCase()=="form")){
dojo.raise("Attempted to encode a non-form element.");
}
if(!_64a){
_64a=dojo.io.formFilter;
}
var enc=/utf/i.test(_649||"")?encodeURIComponent:dojo.string.encodeAscii;
var _64c=[];
for(var i=0;i<_648.elements.length;i++){
var elm=_648.elements[i];
if(!elm||elm.tagName.toLowerCase()=="fieldset"||!_64a(elm)){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
if(elm.options[j].selected){
_64c.push(name+"="+enc(elm.options[j].value));
}
}
}else{
if(dojo.lang.inArray(["radio","checkbox"],type)){
if(elm.checked){
_64c.push(name+"="+enc(elm.value));
}
}else{
_64c.push(name+"="+enc(elm.value));
}
}
}
var _652=_648.getElementsByTagName("input");
for(var i=0;i<_652.length;i++){
var _653=_652[i];
if(_653.type.toLowerCase()=="image"&&_653.form==_648&&_64a(_653)){
var name=enc(_653.name);
_64c.push(name+"="+enc(_653.value));
_64c.push(name+".x=0");
_64c.push(name+".y=0");
}
}
return _64c.join("&")+"&";
};
dojo.io.FormBind=function(args){
this.bindArgs={};
if(args&&args.formNode){
this.init(args);
}else{
if(args){
this.init({formNode:args});
}
}
};
dojo.lang.extend(dojo.io.FormBind,{form:null,bindArgs:null,clickedButton:null,init:function(args){
var form=dojo.byId(args.formNode);
if(!form||!form.tagName||form.tagName.toLowerCase()!="form"){
throw new Error("FormBind: Couldn't apply, invalid form");
}else{
if(this.form==form){
return;
}else{
if(this.form){
throw new Error("FormBind: Already applied to a form");
}
}
}
dojo.lang.mixin(this.bindArgs,args);
this.form=form;
this.connect(form,"onsubmit","submit");
for(var i=0;i<form.elements.length;i++){
var node=form.elements[i];
if(node&&node.type&&dojo.lang.inArray(["submit","button"],node.type.toLowerCase())){
this.connect(node,"onclick","click");
}
}
var _659=form.getElementsByTagName("input");
for(var i=0;i<_659.length;i++){
var _65a=_659[i];
if(_65a.type.toLowerCase()=="image"&&_65a.form==form){
this.connect(_65a,"onclick","click");
}
}
},onSubmit:function(form){
return true;
},submit:function(e){
e.preventDefault();
if(this.onSubmit(this.form)){
dojo.io.bind(dojo.lang.mixin(this.bindArgs,{formFilter:dojo.lang.hitch(this,"formFilter")}));
}
},click:function(e){
var node=e.currentTarget;
if(node.disabled){
return;
}
this.clickedButton=node;
},formFilter:function(node){
var type=(node.type||"").toLowerCase();
var _661=false;
if(node.disabled||!node.name){
_661=false;
}else{
if(dojo.lang.inArray(["submit","button","image"],type)){
if(!this.clickedButton){
this.clickedButton=node;
}
_661=node==this.clickedButton;
}else{
_661=!dojo.lang.inArray(["file","submit","reset","button"],type);
}
}
return _661;
},connect:function(_662,_663,_664){
if(dojo.evalObjPath("dojo.event.connect")){
dojo.event.connect(_662,_663,this,_664);
}else{
var fcn=dojo.lang.hitch(this,_664);
_662[_663]=function(e){
if(!e){
e=window.event;
}
if(!e.currentTarget){
e.currentTarget=e.srcElement;
}
if(!e.preventDefault){
e.preventDefault=function(){
window.event.returnValue=false;
};
}
fcn(e);
};
}
}});
dojo.io.XMLHTTPTransport=new function(){
var _667=this;
var _668={};
this.useCache=false;
this.preventCache=false;
function getCacheKey(url,_66a,_66b){
return url+"|"+_66a+"|"+_66b.toLowerCase();
}
function addToCache(url,_66d,_66e,http){
_668[getCacheKey(url,_66d,_66e)]=http;
}
function getFromCache(url,_671,_672){
return _668[getCacheKey(url,_671,_672)];
}
this.clearCache=function(){
_668={};
};
function doLoad(_673,http,url,_676,_677){
if(((http.status>=200)&&(http.status<300))||(http.status==304)||(location.protocol=="file:"&&(http.status==0||http.status==undefined))||(location.protocol=="chrome:"&&(http.status==0||http.status==undefined))){
var ret;
if(_673.method.toLowerCase()=="head"){
var _679=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _679;
};
var _67a=_679.split(/[\r\n]+/g);
for(var i=0;i<_67a.length;i++){
var pair=_67a[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_673.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=null;
}
}else{
if(_673.mimetype=="text/json"||_673.mimetype=="application/json"){
try{
ret=dj_eval("("+http.responseText+")");
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=false;
}
}else{
if((_673.mimetype=="application/xml")||(_673.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"||!http.getResponseHeader("Content-Type")){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
}
if(_677){
addToCache(url,_676,_673.method,http);
}
_673[(typeof _673.load=="function")?"load":"handle"]("load",ret,http,_673);
}else{
var _67d=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
_673[(typeof _673.error=="function")?"error":"handle"]("error",_67d,http,_673);
}
}
function setHeaders(http,_67f){
if(_67f["headers"]){
for(var _680 in _67f["headers"]){
if(_680.toLowerCase()=="content-type"&&!_67f["contentType"]){
_67f["contentType"]=_67f["headers"][_680];
}else{
http.setRequestHeader(_680,_67f["headers"][_680]);
}
}
}
}
this.inFlight=[];
this.inFlightTimer=null;
this.startWatchingInFlight=function(){
if(!this.inFlightTimer){
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
}
};
this.watchInFlight=function(){
var now=null;
if(!dojo.hostenv._blockAsync&&!_667._blockAsync){
for(var x=this.inFlight.length-1;x>=0;x--){
try{
var tif=this.inFlight[x];
if(!tif||tif.http._aborted||!tif.http.readyState){
this.inFlight.splice(x,1);
continue;
}
if(4==tif.http.readyState){
this.inFlight.splice(x,1);
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
}else{
if(tif.startTime){
if(!now){
now=(new Date()).getTime();
}
if(tif.startTime+(tif.req.timeoutSeconds*1000)<now){
if(typeof tif.http.abort=="function"){
tif.http.abort();
}
this.inFlight.splice(x,1);
tif.req[(typeof tif.req.timeout=="function")?"timeout":"handle"]("timeout",null,tif.http,tif.req);
}
}
}
}
catch(e){
try{
var _684=new dojo.io.Error("XMLHttpTransport.watchInFlight Error: "+e);
tif.req[(typeof tif.req.error=="function")?"error":"handle"]("error",_684,tif.http,tif.req);
}
catch(e2){
dojo.debug("XMLHttpTransport error callback failed: "+e2);
}
}
}
}
clearTimeout(this.inFlightTimer);
if(this.inFlight.length==0){
this.inFlightTimer=null;
return;
}
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
};
var _685=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_686){
return _685&&dojo.lang.inArray(["text/plain","text/html","application/xml","text/xml","text/javascript","text/json","application/json"],(_686["mimetype"].toLowerCase()||""))&&!(_686["formNode"]&&dojo.io.formHasFile(_686["formNode"]));
};
this.multipartBoundary="45309FFF-BD65-4d50-99C9-36986896A96F";
this.bind=function(_687){
if(!_687["url"]){
if(!_687["formNode"]&&(_687["backButton"]||_687["back"]||_687["changeUrl"]||_687["watchForURL"])&&(!djConfig.preventBackButtonFix)){
dojo.deprecated("Using dojo.io.XMLHTTPTransport.bind() to add to browser history without doing an IO request","Use dojo.undo.browser.addToHistory() instead.","0.4");
dojo.undo.browser.addToHistory(_687);
return true;
}
}
var url=_687.url;
var _689="";
if(_687["formNode"]){
var ta=_687.formNode.getAttribute("action");
if((ta)&&(!_687["url"])){
url=ta;
}
var tp=_687.formNode.getAttribute("method");
if((tp)&&(!_687["method"])){
_687.method=tp;
}
_689+=dojo.io.encodeForm(_687.formNode,_687.encoding,_687["formFilter"]);
}
if(url.indexOf("#")>-1){
dojo.debug("Warning: dojo.io.bind: stripping hash values from url:",url);
url=url.split("#")[0];
}
if(_687["file"]){
_687.method="post";
}
if(!_687["method"]){
_687.method="get";
}
if(_687.method.toLowerCase()=="get"){
_687.multipart=false;
}else{
if(_687["file"]){
_687.multipart=true;
}else{
if(!_687["multipart"]){
_687.multipart=false;
}
}
}
if(_687["backButton"]||_687["back"]||_687["changeUrl"]){
dojo.undo.browser.addToHistory(_687);
}
var _68c=_687["content"]||{};
if(_687.sendTransport){
_68c["dojo.transport"]="xmlhttp";
}
do{
if(_687.postContent){
_689=_687.postContent;
break;
}
if(_68c){
_689+=dojo.io.argsFromMap(_68c,_687.encoding);
}
if(_687.method.toLowerCase()=="get"||!_687.multipart){
break;
}
var t=[];
if(_689.length){
var q=_689.split("&");
for(var i=0;i<q.length;++i){
if(q[i].length){
var p=q[i].split("=");
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+p[0]+"\"","",p[1]);
}
}
}
if(_687.file){
if(dojo.lang.isArray(_687.file)){
for(var i=0;i<_687.file.length;++i){
var o=_687.file[i];
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}else{
var o=_687.file;
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}
if(t.length){
t.push("--"+this.multipartBoundary+"--","");
_689=t.join("\r\n");
}
}while(false);
var _692=_687["sync"]?false:true;
var _693=_687["preventCache"]||(this.preventCache==true&&_687["preventCache"]!=false);
var _694=_687["useCache"]==true||(this.useCache==true&&_687["useCache"]!=false);
if(!_693&&_694){
var _695=getFromCache(url,_689,_687.method);
if(_695){
doLoad(_687,_695,url,_689,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject(_687);
var _697=false;
if(_692){
var _698=this.inFlight.push({"req":_687,"http":http,"url":url,"query":_689,"useCache":_694,"startTime":_687.timeoutSeconds?(new Date()).getTime():0});
this.startWatchingInFlight();
}else{
_667._blockAsync=true;
}
if(_687.method.toLowerCase()=="post"){
if(!_687.user){
http.open("POST",url,_692);
}else{
http.open("POST",url,_692,_687.user,_687.password);
}
setHeaders(http,_687);
http.setRequestHeader("Content-Type",_687.multipart?("multipart/form-data; boundary="+this.multipartBoundary):(_687.contentType||"application/x-www-form-urlencoded"));
try{
http.send(_689);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_687,{status:404},url,_689,_694);
}
}else{
var _699=url;
if(_689!=""){
_699+=(_699.indexOf("?")>-1?"&":"?")+_689;
}
if(_693){
_699+=(dojo.string.endsWithAny(_699,"?","&")?"":(_699.indexOf("?")>-1?"&":"?"))+"dojo.preventCache="+new Date().valueOf();
}
if(!_687.user){
http.open(_687.method.toUpperCase(),_699,_692);
}else{
http.open(_687.method.toUpperCase(),_699,_692,_687.user,_687.password);
}
setHeaders(http,_687);
try{
http.send(null);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_687,{status:404},url,_689,_694);
}
}
if(!_692){
doLoad(_687,http,url,_689,_694);
_667._blockAsync=false;
}
_687.abort=function(){
try{
http._aborted=true;
}
catch(e){
}
return http.abort();
};
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
dojo.provide("dojo.io.cookie");
dojo.io.cookie.setCookie=function(name,_69b,days,path,_69e,_69f){
var _6a0=-1;
if(typeof days=="number"&&days>=0){
var d=new Date();
d.setTime(d.getTime()+(days*24*60*60*1000));
_6a0=d.toGMTString();
}
_69b=escape(_69b);
document.cookie=name+"="+_69b+";"+(_6a0!=-1?" expires="+_6a0+";":"")+(path?"path="+path:"")+(_69e?"; domain="+_69e:"")+(_69f?"; secure":"");
};
dojo.io.cookie.set=dojo.io.cookie.setCookie;
dojo.io.cookie.getCookie=function(name){
var idx=document.cookie.lastIndexOf(name+"=");
if(idx==-1){
return null;
}
var _6a4=document.cookie.substring(idx+name.length+1);
var end=_6a4.indexOf(";");
if(end==-1){
end=_6a4.length;
}
_6a4=_6a4.substring(0,end);
_6a4=unescape(_6a4);
return _6a4;
};
dojo.io.cookie.get=dojo.io.cookie.getCookie;
dojo.io.cookie.deleteCookie=function(name){
dojo.io.cookie.setCookie(name,"-",0);
};
dojo.io.cookie.setObjectCookie=function(name,obj,days,path,_6ab,_6ac,_6ad){
if(arguments.length==5){
_6ad=_6ab;
_6ab=null;
_6ac=null;
}
var _6ae=[],_6af,_6b0="";
if(!_6ad){
_6af=dojo.io.cookie.getObjectCookie(name);
}
if(days>=0){
if(!_6af){
_6af={};
}
for(var prop in obj){
if(prop==null){
delete _6af[prop];
}else{
if(typeof obj[prop]=="string"||typeof obj[prop]=="number"){
_6af[prop]=obj[prop];
}
}
}
prop=null;
for(var prop in _6af){
_6ae.push(escape(prop)+"="+escape(_6af[prop]));
}
_6b0=_6ae.join("&");
}
dojo.io.cookie.setCookie(name,_6b0,days,path,_6ab,_6ac);
};
dojo.io.cookie.getObjectCookie=function(name){
var _6b3=null,_6b4=dojo.io.cookie.getCookie(name);
if(_6b4){
_6b3={};
var _6b5=_6b4.split("&");
for(var i=0;i<_6b5.length;i++){
var pair=_6b5[i].split("=");
var _6b8=pair[1];
if(isNaN(_6b8)){
_6b8=unescape(pair[1]);
}
_6b3[unescape(pair[0])]=_6b8;
}
}
return _6b3;
};
dojo.io.cookie.isSupported=function(){
if(typeof navigator.cookieEnabled!="boolean"){
dojo.io.cookie.setCookie("__TestingYourBrowserForCookieSupport__","CookiesAllowed",90,null);
var _6b9=dojo.io.cookie.getCookie("__TestingYourBrowserForCookieSupport__");
navigator.cookieEnabled=(_6b9=="CookiesAllowed");
if(navigator.cookieEnabled){
this.deleteCookie("__TestingYourBrowserForCookieSupport__");
}
}
return navigator.cookieEnabled;
};
if(!dojo.io.cookies){
dojo.io.cookies=dojo.io.cookie;
}
dojo.provide("dojo.io.*");
dojo.provide("dojo.uri.*");
dojo.provide("dojo.io.IframeIO");
dojo.io.createIFrame=function(_6ba,_6bb,uri){
if(window[_6ba]){
return window[_6ba];
}
if(window.frames[_6ba]){
return window.frames[_6ba];
}
var r=dojo.render.html;
var _6be=null;
var turi=uri||dojo.uri.dojoUri("iframe_history.html?noInit=true");
var _6c0=((r.ie)&&(dojo.render.os.win))?"<iframe name=\""+_6ba+"\" src=\""+turi+"\" onload=\""+_6bb+"\">":"iframe";
_6be=document.createElement(_6c0);
with(_6be){
name=_6ba;
setAttribute("name",_6ba);
id=_6ba;
}
dojo.body().appendChild(_6be);
window[_6ba]=_6be;
with(_6be.style){
if(!r.safari){
position="absolute";
}
left=top="0px";
height=width="1px";
visibility="hidden";
}
if(!r.ie){
dojo.io.setIFrameSrc(_6be,turi,true);
_6be.onload=new Function(_6bb);
}
return _6be;
};
dojo.io.IframeTransport=new function(){
var _6c1=this;
this.currentRequest=null;
this.requestQueue=[];
this.iframeName="dojoIoIframe";
this.fireNextRequest=function(){
try{
if((this.currentRequest)||(this.requestQueue.length==0)){
return;
}
var cr=this.currentRequest=this.requestQueue.shift();
cr._contentToClean=[];
var fn=cr["formNode"];
var _6c4=cr["content"]||{};
if(cr.sendTransport){
_6c4["dojo.transport"]="iframe";
}
if(fn){
if(_6c4){
for(var x in _6c4){
if(!fn[x]){
var tn;
if(dojo.render.html.ie){
tn=document.createElement("<input type='hidden' name='"+x+"' value='"+_6c4[x]+"'>");
fn.appendChild(tn);
}else{
tn=document.createElement("input");
fn.appendChild(tn);
tn.type="hidden";
tn.name=x;
tn.value=_6c4[x];
}
cr._contentToClean.push(x);
}else{
fn[x].value=_6c4[x];
}
}
}
if(cr["url"]){
cr._originalAction=fn.getAttribute("action");
fn.setAttribute("action",cr.url);
}
if(!fn.getAttribute("method")){
fn.setAttribute("method",(cr["method"])?cr["method"]:"post");
}
cr._originalTarget=fn.getAttribute("target");
fn.setAttribute("target",this.iframeName);
fn.target=this.iframeName;
fn.submit();
}else{
var _6c7=dojo.io.argsFromMap(this.currentRequest.content);
var _6c8=cr.url+(cr.url.indexOf("?")>-1?"&":"?")+_6c7;
dojo.io.setIFrameSrc(this.iframe,_6c8,true);
}
}
catch(e){
this.iframeOnload(e);
}
};
this.canHandle=function(_6c9){
return ((dojo.lang.inArray(["text/plain","text/html","text/javascript","text/json","application/json"],_6c9["mimetype"]))&&(dojo.lang.inArray(["post","get"],_6c9["method"].toLowerCase()))&&(!((_6c9["sync"])&&(_6c9["sync"]==true))));
};
this.bind=function(_6ca){
if(!this["iframe"]){
this.setUpIframe();
}
this.requestQueue.push(_6ca);
this.fireNextRequest();
return;
};
this.setUpIframe=function(){
this.iframe=dojo.io.createIFrame(this.iframeName,"dojo.io.IframeTransport.iframeOnload();");
};
this.iframeOnload=function(_6cb){
if(!_6c1.currentRequest){
_6c1.fireNextRequest();
return;
}
var req=_6c1.currentRequest;
if(req.formNode){
var _6cd=req._contentToClean;
for(var i=0;i<_6cd.length;i++){
var key=_6cd[i];
if(dojo.render.html.safari){
var _6d0=req.formNode;
for(var j=0;j<_6d0.childNodes.length;j++){
var _6d2=_6d0.childNodes[j];
if(_6d2.name==key){
var _6d3=_6d2.parentNode;
_6d3.removeChild(_6d2);
break;
}
}
}else{
var _6d4=req.formNode[key];
req.formNode.removeChild(_6d4);
req.formNode[key]=null;
}
}
if(req["_originalAction"]){
req.formNode.setAttribute("action",req._originalAction);
}
if(req["_originalTarget"]){
req.formNode.setAttribute("target",req._originalTarget);
req.formNode.target=req._originalTarget;
}
}
var _6d5=function(_6d6){
var doc=_6d6.contentDocument||((_6d6.contentWindow)&&(_6d6.contentWindow.document))||((_6d6.name)&&(document.frames[_6d6.name])&&(document.frames[_6d6.name].document))||null;
return doc;
};
var _6d8;
var _6d9=false;
if(_6cb){
this._callError(req,"IframeTransport Request Error: "+_6cb);
}else{
var ifd=_6d5(_6c1.iframe);
try{
var cmt=req.mimetype;
if((cmt=="text/javascript")||(cmt=="text/json")||(cmt=="application/json")){
var js=ifd.getElementsByTagName("textarea")[0].value;
if(cmt=="text/json"||cmt=="application/json"){
js="("+js+")";
}
_6d8=dj_eval(js);
}else{
if(cmt=="text/html"){
_6d8=ifd;
}else{
_6d8=ifd.getElementsByTagName("textarea")[0].value;
}
}
_6d9=true;
}
catch(e){
this._callError(req,"IframeTransport Error: "+e);
}
}
try{
if(_6d9&&dojo.lang.isFunction(req["load"])){
req.load("load",_6d8,req);
}
}
catch(e){
throw e;
}
finally{
_6c1.currentRequest=null;
_6c1.fireNextRequest();
}
};
this._callError=function(req,_6de){
var _6df=new dojo.io.Error(_6de);
if(dojo.lang.isFunction(req["error"])){
req.error("error",_6df,req);
}
};
dojo.io.transports.addTransport("IframeTransport");
};
dojo.provide("dojo.date");
dojo.deprecated("dojo.date","use one of the modules in dojo.date.* instead","0.5");
dojo.provide("dojo.string.Builder");
dojo.string.Builder=function(str){
this.arrConcat=(dojo.render.html.capable&&dojo.render.html["ie"]);
var a=[];
var b="";
var _6e3=this.length=b.length;
if(this.arrConcat){
if(b.length>0){
a.push(b);
}
b="";
}
this.toString=this.valueOf=function(){
return (this.arrConcat)?a.join(""):b;
};
this.append=function(){
for(var x=0;x<arguments.length;x++){
var s=arguments[x];
if(dojo.lang.isArrayLike(s)){
this.append.apply(this,s);
}else{
if(this.arrConcat){
a.push(s);
}else{
b+=s;
}
_6e3+=s.length;
this.length=_6e3;
}
}
return this;
};
this.clear=function(){
a=[];
b="";
_6e3=this.length=0;
return this;
};
this.remove=function(f,l){
var s="";
if(this.arrConcat){
b=a.join("");
}
a=[];
if(f>0){
s=b.substring(0,(f-1));
}
b=s+b.substring(f+l);
_6e3=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.replace=function(o,n){
if(this.arrConcat){
b=a.join("");
}
a=[];
b=b.replace(o,n);
_6e3=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.insert=function(idx,s){
if(this.arrConcat){
b=a.join("");
}
a=[];
if(idx==0){
b=s+b;
}else{
var t=b.split("");
t.splice(idx,0,s);
b=t.join("");
}
_6e3=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.append.apply(this,arguments);
};
dojo.provide("dojo.string.*");
if(!this["dojo"]){
alert("\"dojo/__package__.js\" is now located at \"dojo/dojo.js\". Please update your includes accordingly");
}
dojo.provide("dojo.json");
dojo.json={jsonRegistry:new dojo.AdapterRegistry(),register:function(name,_6ef,wrap,_6f1){
dojo.json.jsonRegistry.register(name,_6ef,wrap,_6f1);
},evalJson:function(json){
try{
return eval("("+json+")");
}
catch(e){
dojo.debug(e);
return json;
}
},serialize:function(o){
var _6f4=typeof (o);
if(_6f4=="undefined"){
return "undefined";
}else{
if((_6f4=="number")||(_6f4=="boolean")){
return o+"";
}else{
if(o===null){
return "null";
}
}
}
if(_6f4=="string"){
return dojo.string.escapeString(o);
}
var me=arguments.callee;
var _6f6;
if(typeof (o.__json__)=="function"){
_6f6=o.__json__();
if(o!==_6f6){
return me(_6f6);
}
}
if(typeof (o.json)=="function"){
_6f6=o.json();
if(o!==_6f6){
return me(_6f6);
}
}
if(_6f4!="function"&&typeof (o.length)=="number"){
var res=[];
for(var i=0;i<o.length;i++){
var val=me(o[i]);
if(typeof (val)!="string"){
val="undefined";
}
res.push(val);
}
return "["+res.join(",")+"]";
}
try{
window.o=o;
_6f6=dojo.json.jsonRegistry.match(o);
return me(_6f6);
}
catch(e){
}
if(_6f4=="function"){
return null;
}
res=[];
for(var k in o){
var _6fb;
if(typeof (k)=="number"){
_6fb="\""+k+"\"";
}else{
if(typeof (k)=="string"){
_6fb=dojo.string.escapeString(k);
}else{
continue;
}
}
val=me(o[k]);
if(typeof (val)!="string"){
continue;
}
res.push(_6fb+":"+val);
}
return "{"+res.join(",")+"}";
}};
dojo.provide("dojo.Deferred");
dojo.Deferred=function(_6fc){
this.chain=[];
this.id=this._nextId();
this.fired=-1;
this.paused=0;
this.results=[null,null];
this.canceller=_6fc;
this.silentlyCancelled=false;
};
dojo.lang.extend(dojo.Deferred,{getFunctionFromArgs:function(){
var a=arguments;
if((a[0])&&(!a[1])){
if(dojo.lang.isFunction(a[0])){
return a[0];
}else{
if(dojo.lang.isString(a[0])){
return dj_global[a[0]];
}
}
}else{
if((a[0])&&(a[1])){
return dojo.lang.hitch(a[0],a[1]);
}
}
return null;
},makeCalled:function(){
var _6fe=new dojo.Deferred();
_6fe.callback();
return _6fe;
},repr:function(){
var _6ff;
if(this.fired==-1){
_6ff="unfired";
}else{
if(this.fired==0){
_6ff="success";
}else{
_6ff="error";
}
}
return "Deferred("+this.id+", "+_6ff+")";
},toString:dojo.lang.forward("repr"),_nextId:(function(){
var n=1;
return function(){
return n++;
};
})(),cancel:function(){
if(this.fired==-1){
if(this.canceller){
this.canceller(this);
}else{
this.silentlyCancelled=true;
}
if(this.fired==-1){
this.errback(new Error(this.repr()));
}
}else{
if((this.fired==0)&&(this.results[0] instanceof dojo.Deferred)){
this.results[0].cancel();
}
}
},_pause:function(){
this.paused++;
},_unpause:function(){
this.paused--;
if((this.paused==0)&&(this.fired>=0)){
this._fire();
}
},_continue:function(res){
this._resback(res);
this._unpause();
},_resback:function(res){
this.fired=((res instanceof Error)?1:0);
this.results[this.fired]=res;
this._fire();
},_check:function(){
if(this.fired!=-1){
if(!this.silentlyCancelled){
dojo.raise("already called!");
}
this.silentlyCancelled=false;
return;
}
},callback:function(res){
this._check();
this._resback(res);
},errback:function(res){
this._check();
if(!(res instanceof Error)){
res=new Error(res);
}
this._resback(res);
},addBoth:function(cb,cbfn){
var _707=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_707=dojo.lang.curryArguments(null,_707,arguments,2);
}
return this.addCallbacks(_707,_707);
},addCallback:function(cb,cbfn){
var _70a=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_70a=dojo.lang.curryArguments(null,_70a,arguments,2);
}
return this.addCallbacks(_70a,null);
},addErrback:function(cb,cbfn){
var _70d=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_70d=dojo.lang.curryArguments(null,_70d,arguments,2);
}
return this.addCallbacks(null,_70d);
return this.addCallbacks(null,cbfn);
},addCallbacks:function(cb,eb){
this.chain.push([cb,eb]);
if(this.fired>=0){
this._fire();
}
return this;
},_fire:function(){
var _710=this.chain;
var _711=this.fired;
var res=this.results[_711];
var self=this;
var cb=null;
while(_710.length>0&&this.paused==0){
var pair=_710.shift();
var f=pair[_711];
if(f==null){
continue;
}
try{
res=f(res);
_711=((res instanceof Error)?1:0);
if(res instanceof dojo.Deferred){
cb=function(res){
self._continue(res);
};
this._pause();
}
}
catch(err){
_711=1;
res=err;
}
}
this.fired=_711;
this.results[_711]=res;
if((cb)&&(this.paused)){
res.addBoth(cb);
}
}});
dojo.provide("dojo.rpc.RpcService");
dojo.rpc.RpcService=function(url){
if(url){
this.connect(url);
}
};
dojo.lang.extend(dojo.rpc.RpcService,{strictArgChecks:true,serviceUrl:"",parseResults:function(obj){
return obj;
},errorCallback:function(_71a){
return function(type,e){
_71a.errback(new Error(e.message));
};
},resultCallback:function(_71d){
var tf=dojo.lang.hitch(this,function(type,obj,e){
if(obj["error"]!=null){
var err=new Error(obj.error);
err.id=obj.id;
_71d.errback(err);
}else{
var _723=this.parseResults(obj);
_71d.callback(_723);
}
});
return tf;
},generateMethod:function(_724,_725,url){
return dojo.lang.hitch(this,function(){
var _727=new dojo.Deferred();
if((this.strictArgChecks)&&(_725!=null)&&(arguments.length!=_725.length)){
dojo.raise("Invalid number of parameters for remote method.");
}else{
this.bind(_724,arguments,_727,url);
}
return _727;
});
},processSmd:function(_728){
dojo.debug("RpcService: Processing returned SMD.");
if(_728.methods){
dojo.lang.forEach(_728.methods,function(m){
if(m&&m["name"]){
dojo.debug("RpcService: Creating Method: this.",m.name,"()");
this[m.name]=this.generateMethod(m.name,m.parameters,m["url"]||m["serviceUrl"]||m["serviceURL"]);
if(dojo.lang.isFunction(this[m.name])){
dojo.debug("RpcService: Successfully created",m.name,"()");
}else{
dojo.debug("RpcService: Failed to create",m.name,"()");
}
}
},this);
}
this.serviceUrl=_728.serviceUrl||_728.serviceURL;
dojo.debug("RpcService: Dojo RpcService is ready for use.");
},connect:function(_72a){
dojo.debug("RpcService: Attempting to load SMD document from:",_72a);
dojo.io.bind({url:_72a,mimetype:"text/json",load:dojo.lang.hitch(this,function(type,_72c,e){
return this.processSmd(_72c);
}),sync:true});
}});
dojo.provide("dojo.rpc.JsonService");
dojo.rpc.JsonService=function(args){
if(args){
if(dojo.lang.isString(args)){
this.connect(args);
}else{
if(args["smdUrl"]){
this.connect(args.smdUrl);
}
if(args["smdStr"]){
this.processSmd(dj_eval("("+args.smdStr+")"));
}
if(args["smdObj"]){
this.processSmd(args.smdObj);
}
if(args["serviceUrl"]){
this.serviceUrl=args.serviceUrl;
}
if(typeof args["strictArgChecks"]!="undefined"){
this.strictArgChecks=args.strictArgChecks;
}
}
}
};
dojo.inherits(dojo.rpc.JsonService,dojo.rpc.RpcService);
dojo.extend(dojo.rpc.JsonService,{bustCache:false,contentType:"application/json-rpc",lastSubmissionId:0,callRemote:function(_72f,_730){
var _731=new dojo.Deferred();
this.bind(_72f,_730,_731);
return _731;
},bind:function(_732,_733,_734,url){
dojo.io.bind({url:url||this.serviceUrl,postContent:this.createRequest(_732,_733),method:"POST",contentType:this.contentType,mimetype:"text/json",load:this.resultCallback(_734),error:this.errorCallback(_734),preventCache:this.bustCache});
},createRequest:function(_736,_737){
var req={"params":_737,"method":_736,"id":++this.lastSubmissionId};
var data=dojo.json.serialize(req);
dojo.debug("JsonService: JSON-RPC Request: "+data);
return data;
},parseResults:function(obj){
if(!obj){
return;
}
if(obj["Result"]!=null){
return obj["Result"];
}else{
if(obj["result"]!=null){
return obj["result"];
}else{
if(obj["ResultSet"]){
return obj["ResultSet"];
}else{
return obj;
}
}
}
}});
dojo.provide("dojo.rpc.*");
dojo.provide("dojo.xml.Parse");
dojo.xml.Parse=function(){
function getTagName(node){
return ((node)&&(node.tagName)?node.tagName.toLowerCase():"");
}
function getDojoTagName(node){
var _73d=getTagName(node);
if(!_73d){
return "";
}
if((dojo.widget)&&(dojo.widget.tags[_73d])){
return _73d;
}
var p=_73d.indexOf(":");
if(p>=0){
return _73d;
}
if(_73d.substr(0,5)=="dojo:"){
return _73d;
}
if(dojo.render.html.capable&&dojo.render.html.ie&&node.scopeName!="HTML"){
return node.scopeName.toLowerCase()+":"+_73d;
}
if(_73d.substr(0,4)=="dojo"){
return "dojo:"+_73d.substring(4);
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
if(djt.indexOf(":")<0){
djt="dojo:"+djt;
}
return djt.toLowerCase();
}
djt=node.getAttributeNS&&node.getAttributeNS(dojo.dom.dojoml,"type");
if(djt){
return "dojo:"+djt.toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((!dj_global["djConfig"])||(djConfig["ignoreClassNames"])){
var _740=node.className||node.getAttribute("class");
if((_740)&&(_740.indexOf)&&(_740.indexOf("dojo-")!=-1)){
var _741=_740.split(" ");
for(var x=0,c=_741.length;x<c;x++){
if(_741[x].slice(0,5)=="dojo-"){
return "dojo:"+_741[x].substr(5).toLowerCase();
}
}
}
}
return "";
}
this.parseElement=function(node,_745,_746,_747){
var _748={};
var _749=getTagName(node);
if((_749)&&(_749.indexOf("/")==0)){
return null;
}
var _74a=true;
if(_746){
var _74b=getDojoTagName(node);
_749=_74b||_749;
_74a=Boolean(_74b);
}
if(node&&node.getAttribute&&node.getAttribute("parseWidgets")&&node.getAttribute("parseWidgets")=="false"){
return {};
}
_748[_749]=[];
var pos=_749.indexOf(":");
if(pos>0){
var ns=_749.substring(0,pos);
_748["ns"]=ns;
if((dojo.ns)&&(!dojo.ns.allow(ns))){
_74a=false;
}
}
if(_74a){
var _74e=this.parseAttributes(node);
for(var attr in _74e){
if((!_748[_749][attr])||(typeof _748[_749][attr]!="array")){
_748[_749][attr]=[];
}
_748[_749][attr].push(_74e[attr]);
}
_748[_749].nodeRef=node;
_748.tagName=_749;
_748.index=_747||0;
}
var _750=0;
for(var i=0;i<node.childNodes.length;i++){
var tcn=node.childNodes.item(i);
switch(tcn.nodeType){
case dojo.dom.ELEMENT_NODE:
_750++;
var ctn=getDojoTagName(tcn)||getTagName(tcn);
if(!_748[ctn]){
_748[ctn]=[];
}
_748[ctn].push(this.parseElement(tcn,true,_746,_750));
if((tcn.childNodes.length==1)&&(tcn.childNodes.item(0).nodeType==dojo.dom.TEXT_NODE)){
_748[ctn][_748[ctn].length-1].value=tcn.childNodes.item(0).nodeValue;
}
break;
case dojo.dom.TEXT_NODE:
if(node.childNodes.length==1){
_748[_749].push({value:node.childNodes.item(0).nodeValue});
}
break;
default:
break;
}
}
return _748;
};
this.parseAttributes=function(node){
var _755={};
var atts=node.attributes;
var _757,i=0;
while((_757=atts[i++])){
if((dojo.render.html.capable)&&(dojo.render.html.ie)){
if(!_757){
continue;
}
if((typeof _757=="object")&&(typeof _757.nodeValue=="undefined")||(_757.nodeValue==null)||(_757.nodeValue=="")){
continue;
}
}
var nn=_757.nodeName.split(":");
nn=(nn.length==2)?nn[1]:_757.nodeName;
_755[nn]={value:_757.nodeValue};
}
return _755;
};
};
dojo.provide("dojo.xml.*");
dojo.provide("dojo.undo.Manager");
dojo.undo.Manager=function(_75a){
this.clear();
this._parent=_75a;
};
dojo.extend(dojo.undo.Manager,{_parent:null,_undoStack:null,_redoStack:null,_currentManager:null,canUndo:false,canRedo:false,isUndoing:false,isRedoing:false,onUndo:function(_75b,item){
},onRedo:function(_75d,item){
},onUndoAny:function(_75f,item){
},onRedoAny:function(_761,item){
},_updateStatus:function(){
this.canUndo=this._undoStack.length>0;
this.canRedo=this._redoStack.length>0;
},clear:function(){
this._undoStack=[];
this._redoStack=[];
this._currentManager=this;
this.isUndoing=false;
this.isRedoing=false;
this._updateStatus();
},undo:function(){
if(!this.canUndo){
return false;
}
this.endAllTransactions();
this.isUndoing=true;
var top=this._undoStack.pop();
if(top instanceof dojo.undo.Manager){
top.undoAll();
}else{
top.undo();
}
if(top.redo){
this._redoStack.push(top);
}
this.isUndoing=false;
this._updateStatus();
this.onUndo(this,top);
if(!(top instanceof dojo.undo.Manager)){
this.getTop().onUndoAny(this,top);
}
return true;
},redo:function(){
if(!this.canRedo){
return false;
}
this.isRedoing=true;
var top=this._redoStack.pop();
if(top instanceof dojo.undo.Manager){
top.redoAll();
}else{
top.redo();
}
this._undoStack.push(top);
this.isRedoing=false;
this._updateStatus();
this.onRedo(this,top);
if(!(top instanceof dojo.undo.Manager)){
this.getTop().onRedoAny(this,top);
}
return true;
},undoAll:function(){
while(this._undoStack.length>0){
this.undo();
}
},redoAll:function(){
while(this._redoStack.length>0){
this.redo();
}
},push:function(undo,redo,_767){
if(!undo){
return;
}
if(this._currentManager==this){
this._undoStack.push({undo:undo,redo:redo,description:_767});
}else{
this._currentManager.push.apply(this._currentManager,arguments);
}
this._redoStack=[];
this._updateStatus();
},concat:function(_768){
if(!_768){
return;
}
if(this._currentManager==this){
for(var x=0;x<_768._undoStack.length;x++){
this._undoStack.push(_768._undoStack[x]);
}
if(_768._undoStack.length>0){
this._redoStack=[];
}
this._updateStatus();
}else{
this._currentManager.concat.apply(this._currentManager,arguments);
}
},beginTransaction:function(_76a){
if(this._currentManager==this){
var mgr=new dojo.undo.Manager(this);
mgr.description=_76a?_76a:"";
this._undoStack.push(mgr);
this._currentManager=mgr;
return mgr;
}else{
this._currentManager=this._currentManager.beginTransaction.apply(this._currentManager,arguments);
}
},endTransaction:function(_76c){
if(this._currentManager==this){
if(this._parent){
this._parent._currentManager=this._parent;
if(this._undoStack.length==0||_76c){
var idx=dojo.lang.find(this._parent._undoStack,this);
if(idx>=0){
this._parent._undoStack.splice(idx,1);
if(_76c){
for(var x=0;x<this._undoStack.length;x++){
this._parent._undoStack.splice(idx++,0,this._undoStack[x]);
}
this._updateStatus();
}
}
}
return this._parent;
}
}else{
this._currentManager=this._currentManager.endTransaction.apply(this._currentManager,arguments);
}
},endAllTransactions:function(){
while(this._currentManager!=this){
this.endTransaction();
}
},getTop:function(){
if(this._parent){
return this._parent.getTop();
}else{
return this;
}
}});
dojo.provide("dojo.undo.*");
dojo.provide("dojo.crypto");
dojo.crypto.cipherModes={ECB:0,CBC:1,PCBC:2,CFB:3,OFB:4,CTR:5};
dojo.crypto.outputTypes={Base64:0,Hex:1,String:2,Raw:3};
dojo.provide("dojo.crypto.MD5");
dojo.crypto.MD5=new function(){
var _76f=8;
var mask=(1<<_76f)-1;
function toWord(s){
var wa=[];
for(var i=0;i<s.length*_76f;i+=_76f){
wa[i>>5]|=(s.charCodeAt(i/_76f)&mask)<<(i%32);
}
return wa;
}
function toString(wa){
var s=[];
for(var i=0;i<wa.length*32;i+=_76f){
s.push(String.fromCharCode((wa[i>>5]>>>(i%32))&mask));
}
return s.join("");
}
function toHex(wa){
var h="0123456789abcdef";
var s=[];
for(var i=0;i<wa.length*4;i++){
s.push(h.charAt((wa[i>>2]>>((i%4)*8+4))&15)+h.charAt((wa[i>>2]>>((i%4)*8))&15));
}
return s.join("");
}
function toBase64(wa){
var p="=";
var tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var s=[];
for(var i=0;i<wa.length*4;i+=3){
var t=(((wa[i>>2]>>8*(i%4))&255)<<16)|(((wa[i+1>>2]>>8*((i+1)%4))&255)<<8)|((wa[i+2>>2]>>8*((i+2)%4))&255);
for(var j=0;j<4;j++){
if(i*8+j*6>wa.length*32){
s.push(p);
}else{
s.push(tab.charAt((t>>6*(3-j))&63));
}
}
}
return s.join("");
}
function add(x,y){
var l=(x&65535)+(y&65535);
var m=(x>>16)+(y>>16)+(l>>16);
return (m<<16)|(l&65535);
}
function R(n,c){
return (n<<c)|(n>>>(32-c));
}
function C(q,a,b,x,s,t){
return add(R(add(add(a,q),add(x,t)),s),b);
}
function FF(a,b,c,d,x,s,t){
return C((b&c)|((~b)&d),a,b,x,s,t);
}
function GG(a,b,c,d,x,s,t){
return C((b&d)|(c&(~d)),a,b,x,s,t);
}
function HH(a,b,c,d,x,s,t){
return C(b^c^d,a,b,x,s,t);
}
function II(a,b,c,d,x,s,t){
return C(c^(b|(~d)),a,b,x,s,t);
}
function core(x,len){
x[len>>5]|=128<<((len)%32);
x[(((len+64)>>>9)<<4)+14]=len;
var a=1732584193;
var b=-271733879;
var c=-1732584194;
var d=271733878;
for(var i=0;i<x.length;i+=16){
var olda=a;
var oldb=b;
var oldc=c;
var oldd=d;
a=FF(a,b,c,d,x[i+0],7,-680876936);
d=FF(d,a,b,c,x[i+1],12,-389564586);
c=FF(c,d,a,b,x[i+2],17,606105819);
b=FF(b,c,d,a,x[i+3],22,-1044525330);
a=FF(a,b,c,d,x[i+4],7,-176418897);
d=FF(d,a,b,c,x[i+5],12,1200080426);
c=FF(c,d,a,b,x[i+6],17,-1473231341);
b=FF(b,c,d,a,x[i+7],22,-45705983);
a=FF(a,b,c,d,x[i+8],7,1770035416);
d=FF(d,a,b,c,x[i+9],12,-1958414417);
c=FF(c,d,a,b,x[i+10],17,-42063);
b=FF(b,c,d,a,x[i+11],22,-1990404162);
a=FF(a,b,c,d,x[i+12],7,1804603682);
d=FF(d,a,b,c,x[i+13],12,-40341101);
c=FF(c,d,a,b,x[i+14],17,-1502002290);
b=FF(b,c,d,a,x[i+15],22,1236535329);
a=GG(a,b,c,d,x[i+1],5,-165796510);
d=GG(d,a,b,c,x[i+6],9,-1069501632);
c=GG(c,d,a,b,x[i+11],14,643717713);
b=GG(b,c,d,a,x[i+0],20,-373897302);
a=GG(a,b,c,d,x[i+5],5,-701558691);
d=GG(d,a,b,c,x[i+10],9,38016083);
c=GG(c,d,a,b,x[i+15],14,-660478335);
b=GG(b,c,d,a,x[i+4],20,-405537848);
a=GG(a,b,c,d,x[i+9],5,568446438);
d=GG(d,a,b,c,x[i+14],9,-1019803690);
c=GG(c,d,a,b,x[i+3],14,-187363961);
b=GG(b,c,d,a,x[i+8],20,1163531501);
a=GG(a,b,c,d,x[i+13],5,-1444681467);
d=GG(d,a,b,c,x[i+2],9,-51403784);
c=GG(c,d,a,b,x[i+7],14,1735328473);
b=GG(b,c,d,a,x[i+12],20,-1926607734);
a=HH(a,b,c,d,x[i+5],4,-378558);
d=HH(d,a,b,c,x[i+8],11,-2022574463);
c=HH(c,d,a,b,x[i+11],16,1839030562);
b=HH(b,c,d,a,x[i+14],23,-35309556);
a=HH(a,b,c,d,x[i+1],4,-1530992060);
d=HH(d,a,b,c,x[i+4],11,1272893353);
c=HH(c,d,a,b,x[i+7],16,-155497632);
b=HH(b,c,d,a,x[i+10],23,-1094730640);
a=HH(a,b,c,d,x[i+13],4,681279174);
d=HH(d,a,b,c,x[i+0],11,-358537222);
c=HH(c,d,a,b,x[i+3],16,-722521979);
b=HH(b,c,d,a,x[i+6],23,76029189);
a=HH(a,b,c,d,x[i+9],4,-640364487);
d=HH(d,a,b,c,x[i+12],11,-421815835);
c=HH(c,d,a,b,x[i+15],16,530742520);
b=HH(b,c,d,a,x[i+2],23,-995338651);
a=II(a,b,c,d,x[i+0],6,-198630844);
d=II(d,a,b,c,x[i+7],10,1126891415);
c=II(c,d,a,b,x[i+14],15,-1416354905);
b=II(b,c,d,a,x[i+5],21,-57434055);
a=II(a,b,c,d,x[i+12],6,1700485571);
d=II(d,a,b,c,x[i+3],10,-1894986606);
c=II(c,d,a,b,x[i+10],15,-1051523);
b=II(b,c,d,a,x[i+1],21,-2054922799);
a=II(a,b,c,d,x[i+8],6,1873313359);
d=II(d,a,b,c,x[i+15],10,-30611744);
c=II(c,d,a,b,x[i+6],15,-1560198380);
b=II(b,c,d,a,x[i+13],21,1309151649);
a=II(a,b,c,d,x[i+4],6,-145523070);
d=II(d,a,b,c,x[i+11],10,-1120210379);
c=II(c,d,a,b,x[i+2],15,718787259);
b=II(b,c,d,a,x[i+9],21,-343485551);
a=add(a,olda);
b=add(b,oldb);
c=add(c,oldc);
d=add(d,oldd);
}
return [a,b,c,d];
}
function hmac(data,key){
var wa=toWord(key);
if(wa.length>16){
wa=core(wa,key.length*_76f);
}
var l=[],r=[];
for(var i=0;i<16;i++){
l[i]=wa[i]^909522486;
r[i]=wa[i]^1549556828;
}
var h=core(l.concat(toWord(data)),512+data.length*_76f);
return core(r.concat(h),640);
}
this.compute=function(data,_7bd){
var out=_7bd||dojo.crypto.outputTypes.Base64;
switch(out){
case dojo.crypto.outputTypes.Hex:
return toHex(core(toWord(data),data.length*_76f));
case dojo.crypto.outputTypes.String:
return toString(core(toWord(data),data.length*_76f));
default:
return toBase64(core(toWord(data),data.length*_76f));
}
};
this.getHMAC=function(data,key,_7c1){
var out=_7c1||dojo.crypto.outputTypes.Base64;
switch(out){
case dojo.crypto.outputTypes.Hex:
return toHex(hmac(data,key));
case dojo.crypto.outputTypes.String:
return toString(hmac(data,key));
default:
return toBase64(hmac(data,key));
}
};
}();
dojo.provide("dojo.crypto.*");
dojo.provide("dojo.collections.Collections");
dojo.collections.DictionaryEntry=function(k,v){
this.key=k;
this.value=v;
this.valueOf=function(){
return this.value;
};
this.toString=function(){
return String(this.value);
};
};
dojo.collections.Iterator=function(arr){
var a=arr;
var _7c7=0;
this.element=a[_7c7]||null;
this.atEnd=function(){
return (_7c7>=a.length);
};
this.get=function(){
if(this.atEnd()){
return null;
}
this.element=a[_7c7++];
return this.element;
};
this.map=function(fn,_7c9){
var s=_7c9||dj_global;
if(Array.map){
return Array.map(a,fn,s);
}else{
var arr=[];
for(var i=0;i<a.length;i++){
arr.push(fn.call(s,a[i]));
}
return arr;
}
};
this.reset=function(){
_7c7=0;
this.element=a[_7c7];
};
};
dojo.collections.DictionaryIterator=function(obj){
var a=[];
var _7cf={};
for(var p in obj){
if(!_7cf[p]){
a.push(obj[p]);
}
}
var _7d1=0;
this.element=a[_7d1]||null;
this.atEnd=function(){
return (_7d1>=a.length);
};
this.get=function(){
if(this.atEnd()){
return null;
}
this.element=a[_7d1++];
return this.element;
};
this.map=function(fn,_7d3){
var s=_7d3||dj_global;
if(Array.map){
return Array.map(a,fn,s);
}else{
var arr=[];
for(var i=0;i<a.length;i++){
arr.push(fn.call(s,a[i]));
}
return arr;
}
};
this.reset=function(){
_7d1=0;
this.element=a[_7d1];
};
};
dojo.provide("dojo.collections.ArrayList");
dojo.collections.ArrayList=function(arr){
var _7d8=[];
if(arr){
_7d8=_7d8.concat(arr);
}
this.count=_7d8.length;
this.add=function(obj){
_7d8.push(obj);
this.count=_7d8.length;
};
this.addRange=function(a){
if(a.getIterator){
var e=a.getIterator();
while(!e.atEnd()){
this.add(e.get());
}
this.count=_7d8.length;
}else{
for(var i=0;i<a.length;i++){
_7d8.push(a[i]);
}
this.count=_7d8.length;
}
};
this.clear=function(){
_7d8.splice(0,_7d8.length);
this.count=0;
};
this.clone=function(){
return new dojo.collections.ArrayList(_7d8);
};
this.contains=function(obj){
for(var i=0;i<_7d8.length;i++){
if(_7d8[i]==obj){
return true;
}
}
return false;
};
this.forEach=function(fn,_7e0){
var s=_7e0||dj_global;
if(Array.forEach){
Array.forEach(_7d8,fn,s);
}else{
for(var i=0;i<_7d8.length;i++){
fn.call(s,_7d8[i],i,_7d8);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(_7d8);
};
this.indexOf=function(obj){
for(var i=0;i<_7d8.length;i++){
if(_7d8[i]==obj){
return i;
}
}
return -1;
};
this.insert=function(i,obj){
_7d8.splice(i,0,obj);
this.count=_7d8.length;
};
this.item=function(i){
return _7d8[i];
};
this.remove=function(obj){
var i=this.indexOf(obj);
if(i>=0){
_7d8.splice(i,1);
}
this.count=_7d8.length;
};
this.removeAt=function(i){
_7d8.splice(i,1);
this.count=_7d8.length;
};
this.reverse=function(){
_7d8.reverse();
};
this.sort=function(fn){
if(fn){
_7d8.sort(fn);
}else{
_7d8.sort();
}
};
this.setByIndex=function(i,obj){
_7d8[i]=obj;
this.count=_7d8.length;
};
this.toArray=function(){
return [].concat(_7d8);
};
this.toString=function(_7ee){
return _7d8.join((_7ee||","));
};
};
dojo.provide("dojo.collections.Queue");
dojo.collections.Queue=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Queue(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.dequeue=function(){
var r=q.shift();
this.count=q.length;
return r;
};
this.enqueue=function(o){
this.count=q.push(o);
};
this.forEach=function(fn,_7f8){
var s=_7f8||dj_global;
if(Array.forEach){
Array.forEach(q,fn,s);
}else{
for(var i=0;i<q.length;i++){
fn.call(s,q[i],i,q);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[0];
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.collections.Stack");
dojo.collections.Stack=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Stack(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.forEach=function(fn,_802){
var s=_802||dj_global;
if(Array.forEach){
Array.forEach(q,fn,s);
}else{
for(var i=0;i<q.length;i++){
fn.call(s,q[i],i,q);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[(q.length-1)];
};
this.pop=function(){
var r=q.pop();
this.count=q.length;
return r;
};
this.push=function(o){
this.count=q.push(o);
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.dnd.DragAndDrop");
dojo.declare("dojo.dnd.DragSource",null,{type:"",onDragEnd:function(){
},onDragStart:function(){
},onSelected:function(){
},unregister:function(){
dojo.dnd.dragManager.unregisterDragSource(this);
},reregister:function(){
dojo.dnd.dragManager.registerDragSource(this);
}},function(){
var dm=dojo.dnd.dragManager;
if(dm["registerDragSource"]){
dm.registerDragSource(this);
}
});
dojo.declare("dojo.dnd.DragObject",null,{type:"",onDragStart:function(){
},onDragMove:function(){
},onDragOver:function(){
},onDragOut:function(){
},onDragEnd:function(){
},onDragLeave:this.onDragOut,onDragEnter:this.onDragOver,ondragout:this.onDragOut,ondragover:this.onDragOver},function(){
var dm=dojo.dnd.dragManager;
if(dm["registerDragObject"]){
dm.registerDragObject(this);
}
});
dojo.declare("dojo.dnd.DropTarget",null,{acceptsType:function(type){
if(!dojo.lang.inArray(this.acceptedTypes,"*")){
if(!dojo.lang.inArray(this.acceptedTypes,type)){
return false;
}
}
return true;
},accepts:function(_80a){
if(!dojo.lang.inArray(this.acceptedTypes,"*")){
for(var i=0;i<_80a.length;i++){
if(!dojo.lang.inArray(this.acceptedTypes,_80a[i].type)){
return false;
}
}
}
return true;
},unregister:function(){
dojo.dnd.dragManager.unregisterDropTarget(this);
},onDragOver:function(){
},onDragOut:function(){
},onDragMove:function(){
},onDropStart:function(){
},onDrop:function(){
},onDropEnd:function(){
}},function(){
if(this.constructor==dojo.dnd.DropTarget){
return;
}
this.acceptedTypes=[];
dojo.dnd.dragManager.registerDropTarget(this);
});
dojo.dnd.DragEvent=function(){
this.dragSource=null;
this.dragObject=null;
this.target=null;
this.eventStatus="success";
};
dojo.declare("dojo.dnd.DragManager",null,{selectedSources:[],dragObjects:[],dragSources:[],registerDragSource:function(){
},dropTargets:[],registerDropTarget:function(){
},lastDragTarget:null,currentDragTarget:null,onKeyDown:function(){
},onMouseOut:function(){
},onMouseMove:function(){
},onMouseUp:function(){
}});
dojo.provide("dojo.dnd.HtmlDragManager");
dojo.declare("dojo.dnd.HtmlDragManager",dojo.dnd.DragManager,{disabled:false,nestedTargets:false,mouseDownTimer:null,dsCounter:0,dsPrefix:"dojoDragSource",dropTargetDimensions:[],currentDropTarget:null,previousDropTarget:null,_dragTriggered:false,selectedSources:[],dragObjects:[],currentX:null,currentY:null,lastX:null,lastY:null,mouseDownX:null,mouseDownY:null,threshold:7,dropAcceptable:false,cancelEvent:function(e){
e.stopPropagation();
e.preventDefault();
},registerDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _80f=dp+"Idx_"+(this.dsCounter++);
ds.dragSourceId=_80f;
this.dragSources[_80f]=ds;
ds.domNode.setAttribute(dp,_80f);
if(dojo.render.html.ie){
dojo.event.browser.addListener(ds.domNode,"ondragstart",this.cancelEvent);
}
}
},unregisterDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _812=ds.dragSourceId;
delete ds.dragSourceId;
delete this.dragSources[_812];
ds.domNode.setAttribute(dp,null);
if(dojo.render.html.ie){
dojo.event.browser.removeListener(ds.domNode,"ondragstart",this.cancelEvent);
}
}
},registerDropTarget:function(dt){
this.dropTargets.push(dt);
},unregisterDropTarget:function(dt){
var _815=dojo.lang.find(this.dropTargets,dt,true);
if(_815>=0){
this.dropTargets.splice(_815,1);
}
},getDragSource:function(e){
var tn=e.target;
if(tn===dojo.body()){
return;
}
var ta=dojo.html.getAttribute(tn,this.dsPrefix);
while((!ta)&&(tn)){
tn=tn.parentNode;
if((!tn)||(tn===dojo.body())){
return;
}
ta=dojo.html.getAttribute(tn,this.dsPrefix);
}
return this.dragSources[ta];
},onKeyDown:function(e){
},onMouseDown:function(e){
if(this.disabled){
return;
}
if(dojo.render.html.ie){
if(e.button!=1){
return;
}
}else{
if(e.which!=1){
return;
}
}
var _81b=e.target.nodeType==dojo.html.TEXT_NODE?e.target.parentNode:e.target;
if(dojo.html.isTag(_81b,"button","textarea","input","select","option")){
return;
}
var ds=this.getDragSource(e);
if(!ds){
return;
}
if(!dojo.lang.inArray(this.selectedSources,ds)){
this.selectedSources.push(ds);
ds.onSelected();
}
this.mouseDownX=e.pageX;
this.mouseDownY=e.pageY;
e.preventDefault();
dojo.event.connect(document,"onmousemove",this,"onMouseMove");
},onMouseUp:function(e,_81e){
if(this.selectedSources.length==0){
return;
}
this.mouseDownX=null;
this.mouseDownY=null;
this._dragTriggered=false;
e.dragSource=this.dragSource;
if((!e.shiftKey)&&(!e.ctrlKey)){
if(this.currentDropTarget){
this.currentDropTarget.onDropStart();
}
dojo.lang.forEach(this.dragObjects,function(_81f){
var ret=null;
if(!_81f){
return;
}
if(this.currentDropTarget){
e.dragObject=_81f;
var ce=this.currentDropTarget.domNode.childNodes;
if(ce.length>0){
e.dropTarget=ce[0];
while(e.dropTarget==_81f.domNode){
e.dropTarget=e.dropTarget.nextSibling;
}
}else{
e.dropTarget=this.currentDropTarget.domNode;
}
if(this.dropAcceptable){
ret=this.currentDropTarget.onDrop(e);
}else{
this.currentDropTarget.onDragOut(e);
}
}
e.dragStatus=this.dropAcceptable&&ret?"dropSuccess":"dropFailure";
dojo.lang.delayThese([function(){
try{
_81f.dragSource.onDragEnd(e);
}
catch(err){
var _822={};
for(var i in e){
if(i=="type"){
_822.type="mouseup";
continue;
}
_822[i]=e[i];
}
_81f.dragSource.onDragEnd(_822);
}
},function(){
_81f.onDragEnd(e);
}]);
},this);
this.selectedSources=[];
this.dragObjects=[];
this.dragSource=null;
if(this.currentDropTarget){
this.currentDropTarget.onDropEnd();
}
}else{
}
dojo.event.disconnect(document,"onmousemove",this,"onMouseMove");
this.currentDropTarget=null;
},onScroll:function(){
for(var i=0;i<this.dragObjects.length;i++){
if(this.dragObjects[i].updateDragOffset){
this.dragObjects[i].updateDragOffset();
}
}
if(this.dragObjects.length){
this.cacheTargetLocations();
}
},_dragStartDistance:function(x,y){
if((!this.mouseDownX)||(!this.mouseDownX)){
return;
}
var dx=Math.abs(x-this.mouseDownX);
var dx2=dx*dx;
var dy=Math.abs(y-this.mouseDownY);
var dy2=dy*dy;
return parseInt(Math.sqrt(dx2+dy2),10);
},cacheTargetLocations:function(){
dojo.profile.start("cacheTargetLocations");
this.dropTargetDimensions=[];
dojo.lang.forEach(this.dropTargets,function(_82b){
var tn=_82b.domNode;
if(!tn||dojo.lang.find(_82b.acceptedTypes,this.dragSource.type)<0){
return;
}
var abs=dojo.html.getAbsolutePosition(tn,true);
var bb=dojo.html.getBorderBox(tn);
this.dropTargetDimensions.push([[abs.x,abs.y],[abs.x+bb.width,abs.y+bb.height],_82b]);
},this);
dojo.profile.end("cacheTargetLocations");
},onMouseMove:function(e){
if((dojo.render.html.ie)&&(e.button!=1)){
this.currentDropTarget=null;
this.onMouseUp(e,true);
return;
}
if((this.selectedSources.length)&&(!this.dragObjects.length)){
var dx;
var dy;
if(!this._dragTriggered){
this._dragTriggered=(this._dragStartDistance(e.pageX,e.pageY)>this.threshold);
if(!this._dragTriggered){
return;
}
dx=e.pageX-this.mouseDownX;
dy=e.pageY-this.mouseDownY;
}
this.dragSource=this.selectedSources[0];
dojo.lang.forEach(this.selectedSources,function(_832){
if(!_832){
return;
}
var tdo=_832.onDragStart(e);
if(tdo){
tdo.onDragStart(e);
tdo.dragOffset.y+=dy;
tdo.dragOffset.x+=dx;
tdo.dragSource=_832;
this.dragObjects.push(tdo);
}
},this);
this.previousDropTarget=null;
this.cacheTargetLocations();
}
dojo.lang.forEach(this.dragObjects,function(_834){
if(_834){
_834.onDragMove(e);
}
});
if(this.currentDropTarget){
var c=dojo.html.toCoordinateObject(this.currentDropTarget.domNode,true);
var dtp=[[c.x,c.y],[c.x+c.width,c.y+c.height]];
}
if((!this.nestedTargets)&&(dtp)&&(this.isInsideBox(e,dtp))){
if(this.dropAcceptable){
this.currentDropTarget.onDragMove(e,this.dragObjects);
}
}else{
var _837=this.findBestTarget(e);
if(_837.target===null){
if(this.currentDropTarget){
this.currentDropTarget.onDragOut(e);
this.previousDropTarget=this.currentDropTarget;
this.currentDropTarget=null;
}
this.dropAcceptable=false;
return;
}
if(this.currentDropTarget!==_837.target){
if(this.currentDropTarget){
this.previousDropTarget=this.currentDropTarget;
this.currentDropTarget.onDragOut(e);
}
this.currentDropTarget=_837.target;
e.dragObjects=this.dragObjects;
this.dropAcceptable=this.currentDropTarget.onDragOver(e);
}else{
if(this.dropAcceptable){
this.currentDropTarget.onDragMove(e,this.dragObjects);
}
}
}
},findBestTarget:function(e){
var _839=this;
var _83a=new Object();
_83a.target=null;
_83a.points=null;
dojo.lang.every(this.dropTargetDimensions,function(_83b){
if(!_839.isInsideBox(e,_83b)){
return true;
}
_83a.target=_83b[2];
_83a.points=_83b;
return Boolean(_839.nestedTargets);
});
return _83a;
},isInsideBox:function(e,_83d){
if((e.pageX>_83d[0][0])&&(e.pageX<_83d[1][0])&&(e.pageY>_83d[0][1])&&(e.pageY<_83d[1][1])){
return true;
}
return false;
},onMouseOver:function(e){
},onMouseOut:function(e){
}});
dojo.dnd.dragManager=new dojo.dnd.HtmlDragManager();
(function(){
var d=document;
var dm=dojo.dnd.dragManager;
dojo.event.connect(d,"onkeydown",dm,"onKeyDown");
dojo.event.connect(d,"onmouseover",dm,"onMouseOver");
dojo.event.connect(d,"onmouseout",dm,"onMouseOut");
dojo.event.connect(d,"onmousedown",dm,"onMouseDown");
dojo.event.connect(d,"onmouseup",dm,"onMouseUp");
dojo.event.connect(window,"onscroll",dm,"onScroll");
})();
dojo.provide("dojo.html.selection");
dojo.html.selectionType={NONE:0,TEXT:1,CONTROL:2};
dojo.html.clearSelection=function(){
var _842=dojo.global();
var _843=dojo.doc();
try{
if(_842["getSelection"]){
if(dojo.render.html.safari){
_842.getSelection().collapse();
}else{
_842.getSelection().removeAllRanges();
}
}else{
if(_843.selection){
if(_843.selection.empty){
_843.selection.empty();
}else{
if(_843.selection.clear){
_843.selection.clear();
}
}
}
}
return true;
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.html.disableSelection=function(_844){
_844=dojo.byId(_844)||dojo.body();
var h=dojo.render.html;
if(h.mozilla){
_844.style.MozUserSelect="none";
}else{
if(h.safari){
_844.style.KhtmlUserSelect="none";
}else{
if(h.ie){
_844.unselectable="on";
}else{
return false;
}
}
}
return true;
};
dojo.html.enableSelection=function(_846){
_846=dojo.byId(_846)||dojo.body();
var h=dojo.render.html;
if(h.mozilla){
_846.style.MozUserSelect="";
}else{
if(h.safari){
_846.style.KhtmlUserSelect="";
}else{
if(h.ie){
_846.unselectable="off";
}else{
return false;
}
}
}
return true;
};
dojo.html.selectElement=function(_848){
dojo.deprecated("dojo.html.selectElement","replaced by dojo.html.selection.selectElementChildren",0.5);
};
dojo.html.selectInputText=function(_849){
var _84a=dojo.global();
var _84b=dojo.doc();
_849=dojo.byId(_849);
if(_84b["selection"]&&dojo.body()["createTextRange"]){
var _84c=_849.createTextRange();
_84c.moveStart("character",0);
_84c.moveEnd("character",_849.value.length);
_84c.select();
}else{
if(_84a["getSelection"]){
var _84d=_84a.getSelection();
_849.setSelectionRange(0,_849.value.length);
}
}
_849.focus();
};
dojo.html.isSelectionCollapsed=function(){
dojo.deprecated("dojo.html.isSelectionCollapsed","replaced by dojo.html.selection.isCollapsed",0.5);
return dojo.html.selection.isCollapsed();
};
dojo.lang.mixin(dojo.html.selection,{getType:function(){
if(dojo.doc()["selection"]){
return dojo.html.selectionType[dojo.doc().selection.type.toUpperCase()];
}else{
var _84e=dojo.html.selectionType.TEXT;
var oSel;
try{
oSel=dojo.global().getSelection();
}
catch(e){
}
if(oSel&&oSel.rangeCount==1){
var _850=oSel.getRangeAt(0);
if(_850.startContainer==_850.endContainer&&(_850.endOffset-_850.startOffset)==1&&_850.startContainer.nodeType!=dojo.dom.TEXT_NODE){
_84e=dojo.html.selectionType.CONTROL;
}
}
return _84e;
}
},isCollapsed:function(){
var _851=dojo.global();
var _852=dojo.doc();
if(_852["selection"]){
return _852.selection.createRange().text=="";
}else{
if(_851["getSelection"]){
var _853=_851.getSelection();
if(dojo.lang.isString(_853)){
return _853=="";
}else{
return _853.isCollapsed||_853.toString()=="";
}
}
}
},getSelectedElement:function(){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
if(dojo.doc()["selection"]){
var _854=dojo.doc().selection.createRange();
if(_854&&_854.item){
return dojo.doc().selection.createRange().item(0);
}
}else{
var _855=dojo.global().getSelection();
return _855.anchorNode.childNodes[_855.anchorOffset];
}
}
},getParentElement:function(){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
var p=dojo.html.selection.getSelectedElement();
if(p){
return p.parentNode;
}
}else{
if(dojo.doc()["selection"]){
return dojo.doc().selection.createRange().parentElement();
}else{
var _857=dojo.global().getSelection();
if(_857){
var node=_857.anchorNode;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.parentNode;
}
return node;
}
}
}
},getSelectedText:function(){
if(dojo.doc()["selection"]){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
return null;
}
return dojo.doc().selection.createRange().text;
}else{
var _859=dojo.global().getSelection();
if(_859){
return _859.toString();
}
}
},getSelectedHtml:function(){
if(dojo.doc()["selection"]){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
return null;
}
return dojo.doc().selection.createRange().htmlText;
}else{
var _85a=dojo.global().getSelection();
if(_85a&&_85a.rangeCount){
var frag=_85a.getRangeAt(0).cloneContents();
var div=document.createElement("div");
div.appendChild(frag);
return div.innerHTML;
}
return null;
}
},hasAncestorElement:function(_85d){
return (dojo.html.selection.getAncestorElement.apply(this,arguments)!=null);
},getAncestorElement:function(_85e){
var node=dojo.html.selection.getSelectedElement()||dojo.html.selection.getParentElement();
while(node){
if(dojo.html.selection.isTag(node,arguments).length>0){
return node;
}
node=node.parentNode;
}
return null;
},isTag:function(node,tags){
if(node&&node.tagName){
for(var i=0;i<tags.length;i++){
if(node.tagName.toLowerCase()==String(tags[i]).toLowerCase()){
return String(tags[i]).toLowerCase();
}
}
}
return "";
},selectElement:function(_863){
var _864=dojo.global();
var _865=dojo.doc();
_863=dojo.byId(_863);
if(_865.selection&&dojo.body().createTextRange){
try{
var _866=dojo.body().createControlRange();
_866.addElement(_863);
_866.select();
}
catch(e){
dojo.html.selection.selectElementChildren(_863);
}
}else{
if(_864["getSelection"]){
var _867=_864.getSelection();
if(_867["removeAllRanges"]){
var _866=_865.createRange();
_866.selectNode(_863);
_867.removeAllRanges();
_867.addRange(_866);
}
}
}
},selectElementChildren:function(_868){
var _869=dojo.global();
var _86a=dojo.doc();
_868=dojo.byId(_868);
if(_86a.selection&&dojo.body().createTextRange){
var _86b=dojo.body().createTextRange();
_86b.moveToElementText(_868);
_86b.select();
}else{
if(_869["getSelection"]){
var _86c=_869.getSelection();
if(_86c["setBaseAndExtent"]){
_86c.setBaseAndExtent(_868,0,_868,_868.innerText.length-1);
}else{
if(_86c["selectAllChildren"]){
_86c.selectAllChildren(_868);
}
}
}
}
},getBookmark:function(){
var _86d;
var _86e=dojo.doc();
if(_86e["selection"]){
var _86f=_86e.selection.createRange();
_86d=_86f.getBookmark();
}else{
var _870;
try{
_870=dojo.global().getSelection();
}
catch(e){
}
if(_870){
var _86f=_870.getRangeAt(0);
_86d=_86f.cloneRange();
}else{
dojo.debug("No idea how to store the current selection for this browser!");
}
}
return _86d;
},moveToBookmark:function(_871){
var _872=dojo.doc();
if(_872["selection"]){
var _873=_872.selection.createRange();
_873.moveToBookmark(_871);
_873.select();
}else{
var _874;
try{
_874=dojo.global().getSelection();
}
catch(e){
}
if(_874&&_874["removeAllRanges"]){
_874.removeAllRanges();
_874.addRange(_871);
}else{
dojo.debug("No idea how to restore selection for this browser!");
}
}
},collapse:function(_875){
if(dojo.global()["getSelection"]){
var _876=dojo.global().getSelection();
if(_876.removeAllRanges){
if(_875){
_876.collapseToStart();
}else{
_876.collapseToEnd();
}
}else{
dojo.global().getSelection().collapse(_875);
}
}else{
if(dojo.doc().selection){
var _877=dojo.doc().selection.createRange();
_877.collapse(_875);
_877.select();
}
}
},remove:function(){
if(dojo.doc().selection){
var _878=dojo.doc().selection;
if(_878.type.toUpperCase()!="NONE"){
_878.clear();
}
return _878;
}else{
var _878=dojo.global().getSelection();
for(var i=0;i<_878.rangeCount;i++){
_878.getRangeAt(i).deleteContents();
}
return _878;
}
}});
dojo.provide("dojo.html.iframe");
dojo.html.iframeContentWindow=function(_87a){
var win=dojo.html.getDocumentWindow(dojo.html.iframeContentDocument(_87a))||dojo.html.iframeContentDocument(_87a).__parent__||(_87a.name&&document.frames[_87a.name])||null;
return win;
};
dojo.html.iframeContentDocument=function(_87c){
var doc=_87c.contentDocument||((_87c.contentWindow)&&(_87c.contentWindow.document))||((_87c.name)&&(document.frames[_87c.name])&&(document.frames[_87c.name].document))||null;
return doc;
};
dojo.html.BackgroundIframe=function(node){
if(dojo.render.html.ie55||dojo.render.html.ie60){
var html="<iframe src='javascript:false'"+"' style='position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;"+"z-index: -1; filter:Alpha(Opacity=\"0\");' "+">";
this.iframe=dojo.doc().createElement(html);
this.iframe.tabIndex=-1;
if(node){
node.appendChild(this.iframe);
this.domNode=node;
}else{
dojo.body().appendChild(this.iframe);
this.iframe.style.display="none";
}
}
};
dojo.lang.extend(dojo.html.BackgroundIframe,{iframe:null,onResized:function(){
if(this.iframe&&this.domNode&&this.domNode.parentNode){
var _880=dojo.html.getMarginBox(this.domNode);
if(_880.width==0||_880.height==0){
dojo.lang.setTimeout(this,this.onResized,100);
return;
}
this.iframe.style.width=_880.width+"px";
this.iframe.style.height=_880.height+"px";
}
},size:function(node){
if(!this.iframe){
return;
}
var _882=dojo.html.toCoordinateObject(node,true,dojo.html.boxSizing.BORDER_BOX);
this.iframe.style.width=_882.width+"px";
this.iframe.style.height=_882.height+"px";
this.iframe.style.left=_882.left+"px";
this.iframe.style.top=_882.top+"px";
},setZIndex:function(node){
if(!this.iframe){
return;
}
if(dojo.dom.isNode(node)){
this.iframe.style.zIndex=dojo.html.getStyle(node,"z-index")-1;
}else{
if(!isNaN(node)){
this.iframe.style.zIndex=node;
}
}
},show:function(){
if(!this.iframe){
return;
}
this.iframe.style.display="block";
},hide:function(){
if(!this.iframe){
return;
}
this.iframe.style.display="none";
},remove:function(){
dojo.html.removeNode(this.iframe);
}});
dojo.provide("dojo.dnd.HtmlDragAndDrop");
dojo.declare("dojo.dnd.HtmlDragSource",dojo.dnd.DragSource,{dragClass:"",onDragStart:function(){
var _884=new dojo.dnd.HtmlDragObject(this.dragObject,this.type);
if(this.dragClass){
_884.dragClass=this.dragClass;
}
if(this.constrainToContainer){
_884.constrainTo(this.constrainingContainer||this.domNode.parentNode);
}
return _884;
},setDragHandle:function(node){
node=dojo.byId(node);
dojo.dnd.dragManager.unregisterDragSource(this);
this.domNode=node;
dojo.dnd.dragManager.registerDragSource(this);
},setDragTarget:function(node){
this.dragObject=node;
},constrainTo:function(_887){
this.constrainToContainer=true;
if(_887){
this.constrainingContainer=_887;
}
},onSelected:function(){
for(var i=0;i<this.dragObjects.length;i++){
dojo.dnd.dragManager.selectedSources.push(new dojo.dnd.HtmlDragSource(this.dragObjects[i]));
}
},addDragObjects:function(el){
for(var i=0;i<arguments.length;i++){
this.dragObjects.push(arguments[i]);
}
}},function(node,type){
node=dojo.byId(node);
this.dragObjects=[];
this.constrainToContainer=false;
if(node){
this.domNode=node;
this.dragObject=node;
dojo.dnd.DragSource.call(this);
this.type=(type)||(this.domNode.nodeName.toLowerCase());
}
});
dojo.declare("dojo.dnd.HtmlDragObject",dojo.dnd.DragObject,{dragClass:"",opacity:0.5,createIframe:true,disableX:false,disableY:false,createDragNode:function(){
var node=this.domNode.cloneNode(true);
if(this.dragClass){
dojo.html.addClass(node,this.dragClass);
}
if(this.opacity<1){
dojo.html.setOpacity(node,this.opacity);
}
if(node.tagName.toLowerCase()=="tr"){
var doc=this.domNode.ownerDocument;
var _88f=doc.createElement("table");
var _890=doc.createElement("tbody");
_88f.appendChild(_890);
_890.appendChild(node);
var _891=this.domNode.childNodes;
var _892=node.childNodes;
for(var i=0;i<_891.length;i++){
if((_892[i])&&(_892[i].style)){
_892[i].style.width=dojo.html.getContentBox(_891[i]).width+"px";
}
}
node=_88f;
}
if((dojo.render.html.ie55||dojo.render.html.ie60)&&this.createIframe){
with(node.style){
top="0px";
left="0px";
}
var _894=document.createElement("div");
_894.appendChild(node);
this.bgIframe=new dojo.html.BackgroundIframe(_894);
_894.appendChild(this.bgIframe.iframe);
node=_894;
}
node.style.zIndex=999;
return node;
},onDragStart:function(e){
dojo.html.clearSelection();
this.scrollOffset=dojo.html.getScroll().offset;
this.dragStartPosition=dojo.html.getAbsolutePosition(this.domNode,true);
this.dragOffset={y:this.dragStartPosition.y-e.pageY,x:this.dragStartPosition.x-e.pageX};
this.dragClone=this.createDragNode();
this.containingBlockPosition=this.domNode.offsetParent?dojo.html.getAbsolutePosition(this.domNode.offsetParent,true):{x:0,y:0};
if(this.constrainToContainer){
this.constraints=this.getConstraints();
}
with(this.dragClone.style){
position="absolute";
top=this.dragOffset.y+e.pageY+"px";
left=this.dragOffset.x+e.pageX+"px";
}
dojo.body().appendChild(this.dragClone);
dojo.event.connect(this.domNode,"onclick",this,"squelchOnClick");
dojo.event.topic.publish("dragStart",{source:this});
},getConstraints:function(){
if(this.constrainingContainer.nodeName.toLowerCase()=="body"){
var _896=dojo.html.getViewport();
var _897=_896.width;
var _898=_896.height;
var x=0;
var y=0;
}else{
var _89b=dojo.html.getContentBox(this.constrainingContainer);
_897=_89b.width;
_898=_89b.height;
x=this.containingBlockPosition.x+dojo.html.getPixelValue(this.constrainingContainer,"padding-left",true)+dojo.html.getBorderExtent(this.constrainingContainer,"left");
y=this.containingBlockPosition.y+dojo.html.getPixelValue(this.constrainingContainer,"padding-top",true)+dojo.html.getBorderExtent(this.constrainingContainer,"top");
}
var mb=dojo.html.getMarginBox(this.domNode);
return {minX:x,minY:y,maxX:x+_897-mb.width,maxY:y+_898-mb.height};
},updateDragOffset:function(){
var _89d=dojo.html.getScroll().offset;
if(_89d.y!=this.scrollOffset.y){
var diff=_89d.y-this.scrollOffset.y;
this.dragOffset.y+=diff;
this.scrollOffset.y=_89d.y;
}
if(_89d.x!=this.scrollOffset.x){
var diff=_89d.x-this.scrollOffset.x;
this.dragOffset.x+=diff;
this.scrollOffset.x=_89d.x;
}
},onDragMove:function(e){
this.updateDragOffset();
var x=this.dragOffset.x+e.pageX;
var y=this.dragOffset.y+e.pageY;
if(this.constrainToContainer){
if(x<this.constraints.minX){
x=this.constraints.minX;
}
if(y<this.constraints.minY){
y=this.constraints.minY;
}
if(x>this.constraints.maxX){
x=this.constraints.maxX;
}
if(y>this.constraints.maxY){
y=this.constraints.maxY;
}
}
this.setAbsolutePosition(x,y);
dojo.event.topic.publish("dragMove",{source:this});
},setAbsolutePosition:function(x,y){
if(!this.disableY){
this.dragClone.style.top=y+"px";
}
if(!this.disableX){
this.dragClone.style.left=x+"px";
}
},onDragEnd:function(e){
switch(e.dragStatus){
case "dropSuccess":
dojo.html.removeNode(this.dragClone);
this.dragClone=null;
break;
case "dropFailure":
var _8a5=dojo.html.getAbsolutePosition(this.dragClone,true);
var _8a6={left:this.dragStartPosition.x+1,top:this.dragStartPosition.y+1};
var anim=dojo.lfx.slideTo(this.dragClone,_8a6,500,dojo.lfx.easeOut);
var _8a8=this;
dojo.event.connect(anim,"onEnd",function(e){
dojo.lang.setTimeout(function(){
dojo.html.removeNode(_8a8.dragClone);
_8a8.dragClone=null;
},200);
});
anim.play();
break;
}
dojo.event.topic.publish("dragEnd",{source:this});
},squelchOnClick:function(e){
dojo.event.browser.stopEvent(e);
dojo.lang.setTimeout(function(){
dojo.event.disconnect(this.domNode,"onclick",this,"squelchOnClick");
},50);
},constrainTo:function(_8ab){
this.constrainToContainer=true;
if(_8ab){
this.constrainingContainer=_8ab;
}else{
this.constrainingContainer=this.domNode.parentNode;
}
}},function(node,type){
this.domNode=dojo.byId(node);
this.type=type;
this.constrainToContainer=false;
this.dragSource=null;
});
dojo.declare("dojo.dnd.HtmlDropTarget",dojo.dnd.DropTarget,{vertical:false,onDragOver:function(e){
if(!this.accepts(e.dragObjects)){
return false;
}
this.childBoxes=[];
for(var i=0,_8b0;i<this.domNode.childNodes.length;i++){
_8b0=this.domNode.childNodes[i];
if(_8b0.nodeType!=dojo.html.ELEMENT_NODE){
continue;
}
var pos=dojo.html.getAbsolutePosition(_8b0,true);
var _8b2=dojo.html.getBorderBox(_8b0);
this.childBoxes.push({top:pos.y,bottom:pos.y+_8b2.height,left:pos.x,right:pos.x+_8b2.width,height:_8b2.height,width:_8b2.width,node:_8b0});
}
return true;
},_getNodeUnderMouse:function(e){
for(var i=0,_8b5;i<this.childBoxes.length;i++){
with(this.childBoxes[i]){
if(e.pageX>=left&&e.pageX<=right&&e.pageY>=top&&e.pageY<=bottom){
return i;
}
}
}
return -1;
},createDropIndicator:function(){
this.dropIndicator=document.createElement("div");
with(this.dropIndicator.style){
position="absolute";
zIndex=999;
if(this.vertical){
borderLeftWidth="1px";
borderLeftColor="black";
borderLeftStyle="solid";
height=dojo.html.getBorderBox(this.domNode).height+"px";
top=dojo.html.getAbsolutePosition(this.domNode,true).y+"px";
}else{
borderTopWidth="1px";
borderTopColor="black";
borderTopStyle="solid";
width=dojo.html.getBorderBox(this.domNode).width+"px";
left=dojo.html.getAbsolutePosition(this.domNode,true).x+"px";
}
}
},onDragMove:function(e,_8b7){
var i=this._getNodeUnderMouse(e);
if(!this.dropIndicator){
this.createDropIndicator();
}
var _8b9=this.vertical?dojo.html.gravity.WEST:dojo.html.gravity.NORTH;
var hide=false;
if(i<0){
if(this.childBoxes.length){
var _8bb=(dojo.html.gravity(this.childBoxes[0].node,e)&_8b9);
if(_8bb){
hide=true;
}
}else{
var _8bb=true;
}
}else{
var _8bc=this.childBoxes[i];
var _8bb=(dojo.html.gravity(_8bc.node,e)&_8b9);
if(_8bc.node===_8b7[0].dragSource.domNode){
hide=true;
}else{
var _8bd=_8bb?(i>0?this.childBoxes[i-1]:_8bc):(i<this.childBoxes.length-1?this.childBoxes[i+1]:_8bc);
if(_8bd.node===_8b7[0].dragSource.domNode){
hide=true;
}
}
}
if(hide){
this.dropIndicator.style.display="none";
return;
}else{
this.dropIndicator.style.display="";
}
this.placeIndicator(e,_8b7,i,_8bb);
if(!dojo.html.hasParent(this.dropIndicator)){
dojo.body().appendChild(this.dropIndicator);
}
},placeIndicator:function(e,_8bf,_8c0,_8c1){
var _8c2=this.vertical?"left":"top";
var _8c3;
if(_8c0<0){
if(this.childBoxes.length){
_8c3=_8c1?this.childBoxes[0]:this.childBoxes[this.childBoxes.length-1];
}else{
this.dropIndicator.style[_8c2]=dojo.html.getAbsolutePosition(this.domNode,true)[this.vertical?"x":"y"]+"px";
}
}else{
_8c3=this.childBoxes[_8c0];
}
if(_8c3){
this.dropIndicator.style[_8c2]=(_8c1?_8c3[_8c2]:_8c3[this.vertical?"right":"bottom"])+"px";
if(this.vertical){
this.dropIndicator.style.height=_8c3.height+"px";
this.dropIndicator.style.top=_8c3.top+"px";
}else{
this.dropIndicator.style.width=_8c3.width+"px";
this.dropIndicator.style.left=_8c3.left+"px";
}
}
},onDragOut:function(e){
if(this.dropIndicator){
dojo.html.removeNode(this.dropIndicator);
delete this.dropIndicator;
}
},onDrop:function(e){
this.onDragOut(e);
var i=this._getNodeUnderMouse(e);
var _8c7=this.vertical?dojo.html.gravity.WEST:dojo.html.gravity.NORTH;
if(i<0){
if(this.childBoxes.length){
if(dojo.html.gravity(this.childBoxes[0].node,e)&_8c7){
return this.insert(e,this.childBoxes[0].node,"before");
}else{
return this.insert(e,this.childBoxes[this.childBoxes.length-1].node,"after");
}
}
return this.insert(e,this.domNode,"append");
}
var _8c8=this.childBoxes[i];
if(dojo.html.gravity(_8c8.node,e)&_8c7){
return this.insert(e,_8c8.node,"before");
}else{
return this.insert(e,_8c8.node,"after");
}
},insert:function(e,_8ca,_8cb){
var node=e.dragObject.domNode;
if(_8cb=="before"){
return dojo.html.insertBefore(node,_8ca);
}else{
if(_8cb=="after"){
return dojo.html.insertAfter(node,_8ca);
}else{
if(_8cb=="append"){
_8ca.appendChild(node);
return true;
}
}
}
return false;
}},function(node,_8ce){
if(arguments.length==0){
return;
}
this.domNode=dojo.byId(node);
dojo.dnd.DropTarget.call(this);
if(_8ce&&dojo.lang.isString(_8ce)){
_8ce=[_8ce];
}
this.acceptedTypes=_8ce||[];
});
dojo.provide("dojo.dnd.*");
dojo.provide("dojo.ns");
dojo.ns={namespaces:{},failed:{},loading:{},loaded:{},register:function(name,_8d0,_8d1,_8d2){
if(!_8d2||!this.namespaces[name]){
this.namespaces[name]=new dojo.ns.Ns(name,_8d0,_8d1);
}
},allow:function(name){
if(this.failed[name]){
return false;
}
if((djConfig.excludeNamespace)&&(dojo.lang.inArray(djConfig.excludeNamespace,name))){
return false;
}
return ((name==this.dojo)||(!djConfig.includeNamespace)||(dojo.lang.inArray(djConfig.includeNamespace,name)));
},get:function(name){
return this.namespaces[name];
},require:function(name){
var ns=this.namespaces[name];
if((ns)&&(this.loaded[name])){
return ns;
}
if(!this.allow(name)){
return false;
}
if(this.loading[name]){
dojo.debug("dojo.namespace.require: re-entrant request to load namespace \""+name+"\" must fail.");
return false;
}
var req=dojo.require;
this.loading[name]=true;
try{
if(name=="dojo"){
req("dojo.namespaces.dojo");
}else{
if(!dojo.hostenv.moduleHasPrefix(name)){
dojo.registerModulePath(name,"../"+name);
}
req([name,"manifest"].join("."),false,true);
}
if(!this.namespaces[name]){
this.failed[name]=true;
}
}
finally{
this.loading[name]=false;
}
return this.namespaces[name];
}};
dojo.ns.Ns=function(name,_8d9,_8da){
this.name=name;
this.module=_8d9;
this.resolver=_8da;
this._loaded=[];
this._failed=[];
};
dojo.ns.Ns.prototype.resolve=function(name,_8dc,_8dd){
if(!this.resolver||djConfig["skipAutoRequire"]){
return false;
}
var _8de=this.resolver(name,_8dc);
if((_8de)&&(!this._loaded[_8de])&&(!this._failed[_8de])){
var req=dojo.require;
req(_8de,false,true);
if(dojo.hostenv.findModule(_8de,false)){
this._loaded[_8de]=true;
}else{
if(!_8dd){
dojo.raise("dojo.ns.Ns.resolve: module '"+_8de+"' not found after loading via namespace '"+this.name+"'");
}
this._failed[_8de]=true;
}
}
return Boolean(this._loaded[_8de]);
};
dojo.registerNamespace=function(name,_8e1,_8e2){
dojo.ns.register.apply(dojo.ns,arguments);
};
dojo.registerNamespaceResolver=function(name,_8e4){
var n=dojo.ns.namespaces[name];
if(n){
n.resolver=_8e4;
}
};
dojo.registerNamespaceManifest=function(_8e6,path,name,_8e9,_8ea){
dojo.registerModulePath(name,path);
dojo.registerNamespace(name,_8e9,_8ea);
};
dojo.registerNamespace("dojo","dojo.widget");
dojo.provide("dojo.widget.Manager");
dojo.widget.manager=new function(){
this.widgets=[];
this.widgetIds=[];
this.topWidgets={};
var _8eb={};
var _8ec=[];
this.getUniqueId=function(_8ed){
var _8ee;
do{
_8ee=_8ed+"_"+(_8eb[_8ed]!=undefined?++_8eb[_8ed]:_8eb[_8ed]=0);
}while(this.getWidgetById(_8ee));
return _8ee;
};
this.add=function(_8ef){
this.widgets.push(_8ef);
if(!_8ef.extraArgs["id"]){
_8ef.extraArgs["id"]=_8ef.extraArgs["ID"];
}
if(_8ef.widgetId==""){
if(_8ef["id"]){
_8ef.widgetId=_8ef["id"];
}else{
if(_8ef.extraArgs["id"]){
_8ef.widgetId=_8ef.extraArgs["id"];
}else{
_8ef.widgetId=this.getUniqueId(_8ef.widgetType);
}
}
}
if(this.widgetIds[_8ef.widgetId]){
dojo.debug("widget ID collision on ID: "+_8ef.widgetId);
}
this.widgetIds[_8ef.widgetId]=_8ef;
};
this.destroyAll=function(){
for(var x=this.widgets.length-1;x>=0;x--){
try{
this.widgets[x].destroy(true);
delete this.widgets[x];
}
catch(e){
}
}
};
this.remove=function(_8f1){
if(dojo.lang.isNumber(_8f1)){
var tw=this.widgets[_8f1].widgetId;
delete this.widgetIds[tw];
this.widgets.splice(_8f1,1);
}else{
this.removeById(_8f1);
}
};
this.removeById=function(id){
if(!dojo.lang.isString(id)){
id=id["widgetId"];
if(!id){
dojo.debug("invalid widget or id passed to removeById");
return;
}
}
for(var i=0;i<this.widgets.length;i++){
if(this.widgets[i].widgetId==id){
this.remove(i);
break;
}
}
};
this.getWidgetById=function(id){
if(dojo.lang.isString(id)){
return this.widgetIds[id];
}
return id;
};
this.getWidgetsByType=function(type){
var lt=type.toLowerCase();
var _8f8=(type.indexOf(":")<0?function(x){
return x.widgetType.toLowerCase();
}:function(x){
return x.getNamespacedType();
});
var ret=[];
dojo.lang.forEach(this.widgets,function(x){
if(_8f8(x)==lt){
ret.push(x);
}
});
return ret;
};
this.getWidgetsByFilter=function(_8fd,_8fe){
var ret=[];
dojo.lang.every(this.widgets,function(x){
if(_8fd(x)){
ret.push(x);
if(_8fe){
return false;
}
}
return true;
});
return (_8fe?ret[0]:ret);
};
this.getAllWidgets=function(){
return this.widgets.concat();
};
this.getWidgetByNode=function(node){
var w=this.getAllWidgets();
node=dojo.byId(node);
for(var i=0;i<w.length;i++){
if(w[i].domNode==node){
return w[i];
}
}
return null;
};
this.byId=this.getWidgetById;
this.byType=this.getWidgetsByType;
this.byFilter=this.getWidgetsByFilter;
this.byNode=this.getWidgetByNode;
var _904={};
var _905=["dojo.widget"];
for(var i=0;i<_905.length;i++){
_905[_905[i]]=true;
}
this.registerWidgetPackage=function(_907){
if(!_905[_907]){
_905[_907]=true;
_905.push(_907);
}
};
this.getWidgetPackageList=function(){
return dojo.lang.map(_905,function(elt){
return (elt!==true?elt:undefined);
});
};
this.getImplementation=function(_909,_90a,_90b,ns){
var impl=this.getImplementationName(_909,ns);
if(impl){
var ret=_90a?new impl(_90a):new impl();
return ret;
}
};
function buildPrefixCache(){
for(var _90f in dojo.render){
if(dojo.render[_90f]["capable"]===true){
var _910=dojo.render[_90f].prefixes;
for(var i=0;i<_910.length;i++){
_8ec.push(_910[i].toLowerCase());
}
}
}
}
var _912=function(_913,_914){
if(!_914){
return null;
}
for(var i=0,l=_8ec.length,_917;i<=l;i++){
_917=(i<l?_914[_8ec[i]]:_914);
if(!_917){
continue;
}
for(var name in _917){
if(name.toLowerCase()==_913){
return _917[name];
}
}
}
return null;
};
var _919=function(_91a,_91b){
var _91c=dojo.evalObjPath(_91b,false);
return (_91c?_912(_91a,_91c):null);
};
this.getImplementationName=function(_91d,ns){
var _91f=_91d.toLowerCase();
ns=ns||"dojo";
var imps=_904[ns]||(_904[ns]={});
var impl=imps[_91f];
if(impl){
return impl;
}
if(!_8ec.length){
buildPrefixCache();
}
var _922=dojo.ns.get(ns);
if(!_922){
dojo.ns.register(ns,ns+".widget");
_922=dojo.ns.get(ns);
}
if(_922){
_922.resolve(_91d);
}
impl=_919(_91f,_922.module);
if(impl){
return (imps[_91f]=impl);
}
_922=dojo.ns.require(ns);
if((_922)&&(_922.resolver)){
_922.resolve(_91d);
impl=_919(_91f,_922.module);
if(impl){
return (imps[_91f]=impl);
}
}
dojo.deprecated("dojo.widget.Manager.getImplementationName","Could not locate widget implementation for \""+_91d+"\" in \""+_922.module+"\" registered to namespace \""+_922.name+"\". "+"Developers must specify correct namespaces for all non-Dojo widgets","0.5");
for(var i=0;i<_905.length;i++){
impl=_919(_91f,_905[i]);
if(impl){
return (imps[_91f]=impl);
}
}
throw new Error("Could not locate widget implementation for \""+_91d+"\" in \""+_922.module+"\" registered to namespace \""+_922.name+"\"");
};
this.resizing=false;
this.onWindowResized=function(){
if(this.resizing){
return;
}
try{
this.resizing=true;
for(var id in this.topWidgets){
var _925=this.topWidgets[id];
if(_925.checkSize){
_925.checkSize();
}
}
}
catch(e){
}
finally{
this.resizing=false;
}
};
if(typeof window!="undefined"){
dojo.addOnLoad(this,"onWindowResized");
dojo.event.connect(window,"onresize",this,"onWindowResized");
}
};
(function(){
var dw=dojo.widget;
var dwm=dw.manager;
var h=dojo.lang.curry(dojo.lang,"hitch",dwm);
var g=function(_92a,_92b){
dw[(_92b||_92a)]=h(_92a);
};
g("add","addWidget");
g("destroyAll","destroyAllWidgets");
g("remove","removeWidget");
g("removeById","removeWidgetById");
g("getWidgetById");
g("getWidgetById","byId");
g("getWidgetsByType");
g("getWidgetsByFilter");
g("getWidgetsByType","byType");
g("getWidgetsByFilter","byFilter");
g("getWidgetByNode","byNode");
dw.all=function(n){
var _92d=dwm.getAllWidgets.apply(dwm,arguments);
if(arguments.length>0){
return _92d[n];
}
return _92d;
};
g("registerWidgetPackage");
g("getImplementation","getWidgetImplementation");
g("getImplementationName","getWidgetImplementationName");
dw.widgets=dwm.widgets;
dw.widgetIds=dwm.widgetIds;
dw.root=dwm.root;
})();
dojo.provide("dojo.a11y");
dojo.a11y={imgPath:dojo.uri.dojoUri("src/widget/templates/images"),doAccessibleCheck:true,accessible:null,checkAccessible:function(){
if(this.accessible===null){
this.accessible=false;
if(this.doAccessibleCheck==true){
this.accessible=this.testAccessible();
}
}
return this.accessible;
},testAccessible:function(){
this.accessible=false;
if(dojo.render.html.ie||dojo.render.html.mozilla){
var div=document.createElement("div");
div.style.backgroundImage="url(\""+this.imgPath+"/tab_close.gif\")";
dojo.body().appendChild(div);
var _92f=null;
if(window.getComputedStyle){
var _930=getComputedStyle(div,"");
_92f=_930.getPropertyValue("background-image");
}else{
_92f=div.currentStyle.backgroundImage;
}
var _931=false;
if(_92f!=null&&(_92f=="none"||_92f=="url(invalid-url:)")){
this.accessible=true;
}
dojo.body().removeChild(div);
}
return this.accessible;
},setCheckAccessible:function(_932){
this.doAccessibleCheck=_932;
},setAccessibleMode:function(){
if(this.accessible===null){
if(this.checkAccessible()){
dojo.render.html.prefixes.unshift("a11y");
}
}
return this.accessible;
}};
dojo.provide("dojo.widget.Widget");
dojo.declare("dojo.widget.Widget",null,function(){
this.children=[];
this.extraArgs={};
},{parent:null,children:[],extraArgs:{},isTopLevel:false,isModal:false,isEnabled:true,isHidden:false,isContainer:false,widgetId:"",widgetType:"Widget",ns:"dojo",getNamespacedType:function(){
return (this.ns?this.ns+":"+this.widgetType:this.widgetType).toLowerCase();
},toString:function(){
return "[Widget "+this.getNamespacedType()+", "+(this.widgetId||"NO ID")+"]";
},repr:function(){
return this.toString();
},enable:function(){
this.isEnabled=true;
},disable:function(){
this.isEnabled=false;
},hide:function(){
this.isHidden=true;
},show:function(){
this.isHidden=false;
},onResized:function(){
this.notifyChildrenOfResize();
},notifyChildrenOfResize:function(){
for(var i=0;i<this.children.length;i++){
var _934=this.children[i];
if(_934.onResized){
_934.onResized();
}
}
},create:function(args,_936,_937,ns){
if(ns){
this.ns=ns;
}
this.satisfyPropertySets(args,_936,_937);
this.mixInProperties(args,_936,_937);
this.postMixInProperties(args,_936,_937);
dojo.widget.manager.add(this);
this.buildRendering(args,_936,_937);
this.initialize(args,_936,_937);
this.postInitialize(args,_936,_937);
this.postCreate(args,_936,_937);
return this;
},destroy:function(_939){
this.destroyChildren();
this.uninitialize();
this.destroyRendering(_939);
dojo.widget.manager.removeById(this.widgetId);
},destroyChildren:function(){
var _93a;
var i=0;
while(this.children.length>i){
_93a=this.children[i];
if(_93a instanceof dojo.widget.Widget){
this.removeChild(_93a);
_93a.destroy();
continue;
}
i++;
}
},getChildrenOfType:function(type,_93d){
var ret=[];
var _93f=dojo.lang.isFunction(type);
if(!_93f){
type=type.toLowerCase();
}
for(var x=0;x<this.children.length;x++){
if(_93f){
if(this.children[x] instanceof type){
ret.push(this.children[x]);
}
}else{
if(this.children[x].widgetType.toLowerCase()==type){
ret.push(this.children[x]);
}
}
if(_93d){
ret=ret.concat(this.children[x].getChildrenOfType(type,_93d));
}
}
return ret;
},getDescendants:function(){
var _941=[];
var _942=[this];
var elem;
while((elem=_942.pop())){
_941.push(elem);
if(elem.children){
dojo.lang.forEach(elem.children,function(elem){
_942.push(elem);
});
}
}
return _941;
},isFirstChild:function(){
return this===this.parent.children[0];
},isLastChild:function(){
return this===this.parent.children[this.parent.children.length-1];
},satisfyPropertySets:function(args){
return args;
},mixInProperties:function(args,frag){
if((args["fastMixIn"])||(frag["fastMixIn"])){
for(var x in args){
this[x]=args[x];
}
return;
}
var _949;
var _94a=dojo.widget.lcArgsCache[this.widgetType];
if(_94a==null){
_94a={};
for(var y in this){
_94a[((new String(y)).toLowerCase())]=y;
}
dojo.widget.lcArgsCache[this.widgetType]=_94a;
}
var _94c={};
for(var x in args){
if(!this[x]){
var y=_94a[(new String(x)).toLowerCase()];
if(y){
args[y]=args[x];
x=y;
}
}
if(_94c[x]){
continue;
}
_94c[x]=true;
if((typeof this[x])!=(typeof _949)){
if(typeof args[x]!="string"){
this[x]=args[x];
}else{
if(dojo.lang.isString(this[x])){
this[x]=args[x];
}else{
if(dojo.lang.isNumber(this[x])){
this[x]=new Number(args[x]);
}else{
if(dojo.lang.isBoolean(this[x])){
this[x]=(args[x].toLowerCase()=="false")?false:true;
}else{
if(dojo.lang.isFunction(this[x])){
if(args[x].search(/[^\w\.]+/i)==-1){
this[x]=dojo.evalObjPath(args[x],false);
}else{
var tn=dojo.lang.nameAnonFunc(new Function(args[x]),this);
dojo.event.kwConnect({srcObj:this,srcFunc:x,adviceObj:this,adviceFunc:tn});
}
}else{
if(dojo.lang.isArray(this[x])){
this[x]=args[x].split(";");
}else{
if(this[x] instanceof Date){
this[x]=new Date(Number(args[x]));
}else{
if(typeof this[x]=="object"){
if(this[x] instanceof dojo.uri.Uri){
this[x]=args[x];
}else{
var _94e=args[x].split(";");
for(var y=0;y<_94e.length;y++){
var si=_94e[y].indexOf(":");
if((si!=-1)&&(_94e[y].length>si)){
this[x][_94e[y].substr(0,si).replace(/^\s+|\s+$/g,"")]=_94e[y].substr(si+1);
}
}
}
}else{
this[x]=args[x];
}
}
}
}
}
}
}
}
}else{
this.extraArgs[x.toLowerCase()]=args[x];
}
}
},postMixInProperties:function(args,frag,_952){
},initialize:function(args,frag,_955){
return false;
},postInitialize:function(args,frag,_958){
return false;
},postCreate:function(args,frag,_95b){
return false;
},uninitialize:function(){
return false;
},buildRendering:function(args,frag,_95e){
dojo.unimplemented("dojo.widget.Widget.buildRendering, on "+this.toString()+", ");
return false;
},destroyRendering:function(){
dojo.unimplemented("dojo.widget.Widget.destroyRendering");
return false;
},cleanUp:function(){
dojo.unimplemented("dojo.widget.Widget.cleanUp");
return false;
},addedTo:function(_95f){
},addChild:function(_960){
dojo.unimplemented("dojo.widget.Widget.addChild");
return false;
},removeChild:function(_961){
for(var x=0;x<this.children.length;x++){
if(this.children[x]===_961){
this.children.splice(x,1);
break;
}
}
return _961;
},resize:function(_963,_964){
this.setWidth(_963);
this.setHeight(_964);
},setWidth:function(_965){
if((typeof _965=="string")&&(_965.substr(-1)=="%")){
this.setPercentageWidth(_965);
}else{
this.setNativeWidth(_965);
}
},setHeight:function(_966){
if((typeof _966=="string")&&(_966.substr(-1)=="%")){
this.setPercentageHeight(_966);
}else{
this.setNativeHeight(_966);
}
},setPercentageHeight:function(_967){
return false;
},setNativeHeight:function(_968){
return false;
},setPercentageWidth:function(_969){
return false;
},setNativeWidth:function(_96a){
return false;
},getPreviousSibling:function(){
var idx=this.getParentIndex();
if(idx<=0){
return null;
}
return this.parent.children[idx-1];
},getSiblings:function(){
return this.parent.children;
},getParentIndex:function(){
return dojo.lang.indexOf(this.parent.children,this,true);
},getNextSibling:function(){
var idx=this.getParentIndex();
if(idx==this.parent.children.length-1){
return null;
}
if(idx<0){
return null;
}
return this.parent.children[idx+1];
}});
dojo.widget.lcArgsCache={};
dojo.widget.tags={};
dojo.widget.tags.addParseTreeHandler=function(type){
dojo.deprecated("addParseTreeHandler",". ParseTreeHandlers are now reserved for components. Any unfiltered DojoML tag without a ParseTreeHandler is assumed to be a widget","0.5");
};
dojo.widget.tags["dojo:propertyset"]=function(_96e,_96f,_970){
var _971=_96f.parseProperties(_96e["dojo:propertyset"]);
};
dojo.widget.tags["dojo:connect"]=function(_972,_973,_974){
var _975=_973.parseProperties(_972["dojo:connect"]);
};
dojo.widget.buildWidgetFromParseTree=function(type,frag,_978,_979,_97a,_97b){
dojo.a11y.setAccessibleMode();
var _97c=type.split(":");
_97c=(_97c.length==2)?_97c[1]:type;
var _97d=_97b||_978.parseProperties(frag[frag["ns"]+":"+_97c]);
var _97e=dojo.widget.manager.getImplementation(_97c,null,null,frag["ns"]);
if(!_97e){
throw new Error("cannot find \""+type+"\" widget");
}else{
if(!_97e.create){
throw new Error("\""+type+"\" widget object has no \"create\" method and does not appear to implement *Widget");
}
}
_97d["dojoinsertionindex"]=_97a;
var ret=_97e.create(_97d,frag,_979,frag["ns"]);
return ret;
};
dojo.widget.defineWidget=function(_980,_981,_982,init,_984){
if(dojo.lang.isString(arguments[3])){
dojo.widget._defineWidget(arguments[0],arguments[3],arguments[1],arguments[4],arguments[2]);
}else{
var args=[arguments[0]],p=3;
if(dojo.lang.isString(arguments[1])){
args.push(arguments[1],arguments[2]);
}else{
args.push("",arguments[1]);
p=2;
}
if(dojo.lang.isFunction(arguments[p])){
args.push(arguments[p],arguments[p+1]);
}else{
args.push(null,arguments[p]);
}
dojo.widget._defineWidget.apply(this,args);
}
};
dojo.widget.defineWidget.renderers="html|svg|vml";
dojo.widget._defineWidget=function(_987,_988,_989,init,_98b){
var _98c=_987.split(".");
var type=_98c.pop();
var regx="\\.("+(_988?_988+"|":"")+dojo.widget.defineWidget.renderers+")\\.";
var r=_987.search(new RegExp(regx));
_98c=(r<0?_98c.join("."):_987.substr(0,r));
dojo.widget.manager.registerWidgetPackage(_98c);
var pos=_98c.indexOf(".");
var _991=(pos>-1)?_98c.substring(0,pos):_98c;
_98b=(_98b)||{};
_98b.widgetType=type;
if((!init)&&(_98b["classConstructor"])){
init=_98b.classConstructor;
delete _98b.classConstructor;
}
dojo.declare(_987,_989,init,_98b);
};
dojo.provide("dojo.widget.Parse");
dojo.widget.Parse=function(_992){
this.propertySetsList=[];
this.fragment=_992;
this.createComponents=function(frag,_994){
var _995=[];
var _996=false;
try{
if((frag)&&(frag["tagName"])&&(frag!=frag["nodeRef"])){
var _997=dojo.widget.tags;
var tna=String(frag["tagName"]).split(";");
for(var x=0;x<tna.length;x++){
var ltn=(tna[x].replace(/^\s+|\s+$/g,"")).toLowerCase();
frag.tagName=ltn;
if(_997[ltn]){
_996=true;
var ret=_997[ltn](frag,this,_994,frag["index"]);
_995.push(ret);
}else{
if(ltn.indexOf(":")==-1){
ltn="dojo:"+ltn;
}
var ret=dojo.widget.buildWidgetFromParseTree(ltn,frag,this,_994,frag["index"]);
if(ret){
_996=true;
_995.push(ret);
}
}
}
}
}
catch(e){
dojo.debug("dojo.widget.Parse: error:"+e);
}
if(!_996){
_995=_995.concat(this.createSubComponents(frag,_994));
}
return _995;
};
this.createSubComponents=function(_99c,_99d){
var frag,_99f=[];
for(var item in _99c){
frag=_99c[item];
if((frag)&&(typeof frag=="object")&&(frag!=_99c.nodeRef)&&(frag!=_99c["tagName"])){
_99f=_99f.concat(this.createComponents(frag,_99d));
}
}
return _99f;
};
this.parsePropertySets=function(_9a1){
return [];
};
this.parseProperties=function(_9a2){
var _9a3={};
for(var item in _9a2){
if((_9a2[item]==_9a2["tagName"])||(_9a2[item]==_9a2.nodeRef)){
}else{
if((_9a2[item]["tagName"])&&(dojo.widget.tags[_9a2[item].tagName.toLowerCase()])){
}else{
if((_9a2[item][0])&&(_9a2[item][0].value!="")&&(_9a2[item][0].value!=null)){
try{
if(item.toLowerCase()=="dataprovider"){
var _9a5=this;
this.getDataProvider(_9a5,_9a2[item][0].value);
_9a3.dataProvider=this.dataProvider;
}
_9a3[item]=_9a2[item][0].value;
var _9a6=this.parseProperties(_9a2[item]);
for(var _9a7 in _9a6){
_9a3[_9a7]=_9a6[_9a7];
}
}
catch(e){
dojo.debug(e);
}
}
}
switch(item.toLowerCase()){
case "checked":
case "disabled":
if(typeof _9a3[item]!="boolean"){
_9a3[item]=true;
}
break;
}
}
}
return _9a3;
};
this.getDataProvider=function(_9a8,_9a9){
dojo.io.bind({url:_9a9,load:function(type,_9ab){
if(type=="load"){
_9a8.dataProvider=_9ab;
}
},mimetype:"text/javascript",sync:true});
};
this.getPropertySetById=function(_9ac){
for(var x=0;x<this.propertySetsList.length;x++){
if(_9ac==this.propertySetsList[x]["id"][0].value){
return this.propertySetsList[x];
}
}
return "";
};
this.getPropertySetsByType=function(_9ae){
var _9af=[];
for(var x=0;x<this.propertySetsList.length;x++){
var cpl=this.propertySetsList[x];
var cpcc=cpl["componentClass"]||cpl["componentType"]||null;
var _9b3=this.propertySetsList[x]["id"][0].value;
if((cpcc)&&(_9b3==cpcc[0].value)){
_9af.push(cpl);
}
}
return _9af;
};
this.getPropertySets=function(_9b4){
var ppl="dojo:propertyproviderlist";
var _9b6=[];
var _9b7=_9b4["tagName"];
if(_9b4[ppl]){
var _9b8=_9b4[ppl].value.split(" ");
for(var _9b9 in _9b8){
if((_9b9.indexOf("..")==-1)&&(_9b9.indexOf("://")==-1)){
var _9ba=this.getPropertySetById(_9b9);
if(_9ba!=""){
_9b6.push(_9ba);
}
}else{
}
}
}
return (this.getPropertySetsByType(_9b7)).concat(_9b6);
};
this.createComponentFromScript=function(_9bb,_9bc,_9bd,ns){
_9bd.fastMixIn=true;
var ltn=(ns||"dojo")+":"+_9bc.toLowerCase();
if(dojo.widget.tags[ltn]){
return [dojo.widget.tags[ltn](_9bd,this,null,null,_9bd)];
}
return [dojo.widget.buildWidgetFromParseTree(ltn,_9bd,this,null,null,_9bd)];
};
};
dojo.widget._parser_collection={"dojo":new dojo.widget.Parse()};
dojo.widget.getParser=function(name){
if(!name){
name="dojo";
}
if(!this._parser_collection[name]){
this._parser_collection[name]=new dojo.widget.Parse();
}
return this._parser_collection[name];
};
dojo.widget.createWidget=function(name,_9c2,_9c3,_9c4){
var _9c5=false;
var _9c6=(typeof name=="string");
if(_9c6){
var pos=name.indexOf(":");
var ns=(pos>-1)?name.substring(0,pos):"dojo";
if(pos>-1){
name=name.substring(pos+1);
}
var _9c9=name.toLowerCase();
var _9ca=ns+":"+_9c9;
_9c5=(dojo.byId(name)&&(!dojo.widget.tags[_9ca]));
}
if((arguments.length==1)&&((_9c5)||(!_9c6))){
var xp=new dojo.xml.Parse();
var tn=(_9c5)?dojo.byId(name):name;
return dojo.widget.getParser().createComponents(xp.parseElement(tn,null,true))[0];
}
function fromScript(_9cd,name,_9cf,ns){
_9cf[_9ca]={dojotype:[{value:_9c9}],nodeRef:_9cd,fastMixIn:true};
_9cf.ns=ns;
return dojo.widget.getParser().createComponentFromScript(_9cd,name,_9cf,ns);
}
_9c2=_9c2||{};
var _9d1=false;
var tn=null;
var h=dojo.render.html.capable;
if(h){
tn=document.createElement("span");
}
if(!_9c3){
_9d1=true;
_9c3=tn;
if(h){
dojo.body().appendChild(_9c3);
}
}else{
if(_9c4){
dojo.dom.insertAtPosition(tn,_9c3,_9c4);
}else{
tn=_9c3;
}
}
var _9d3=fromScript(tn,name.toLowerCase(),_9c2,ns);
if((!_9d3)||(!_9d3[0])||(typeof _9d3[0].widgetType=="undefined")){
throw new Error("createWidget: Creation of \""+name+"\" widget failed.");
}
try{
if(_9d1){
if(_9d3[0].domNode.parentNode){
_9d3[0].domNode.parentNode.removeChild(_9d3[0].domNode);
}
}
}
catch(e){
dojo.debug(e);
}
return _9d3[0];
};
dojo.provide("dojo.widget.DomWidget");
dojo.widget._cssFiles={};
dojo.widget._cssStrings={};
dojo.widget._templateCache={};
dojo.widget.defaultStrings={dojoRoot:dojo.hostenv.getBaseScriptUri(),baseScriptUri:dojo.hostenv.getBaseScriptUri()};
dojo.widget.fillFromTemplateCache=function(obj,_9d5,_9d6,_9d7){
var _9d8=_9d5||obj.templatePath;
var _9d9=dojo.widget._templateCache;
if(!obj["widgetType"]){
do{
var _9da="__dummyTemplate__"+dojo.widget._templateCache.dummyCount++;
}while(_9d9[_9da]);
obj.widgetType=_9da;
}
var wt=obj.widgetType;
var ts=_9d9[wt];
if(!ts){
_9d9[wt]={"string":null,"node":null};
if(_9d7){
ts={};
}else{
ts=_9d9[wt];
}
}
if((!obj.templateString)&&(!_9d7)){
obj.templateString=_9d6||ts["string"];
}
if((!obj.templateNode)&&(!_9d7)){
obj.templateNode=ts["node"];
}
if((!obj.templateNode)&&(!obj.templateString)&&(_9d8)){
var _9dd=dojo.hostenv.getText(_9d8);
if(_9dd){
_9dd=_9dd.replace(/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im,"");
var _9de=_9dd.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_9de){
_9dd=_9de[1];
}
}else{
_9dd="";
}
obj.templateString=_9dd;
if(!_9d7){
_9d9[wt]["string"]=_9dd;
}
}
if((!ts["string"])&&(!_9d7)){
ts.string=obj.templateString;
}
};
dojo.widget._templateCache.dummyCount=0;
dojo.widget.attachProperties=["dojoAttachPoint","id"];
dojo.widget.eventAttachProperty="dojoAttachEvent";
dojo.widget.onBuildProperty="dojoOnBuild";
dojo.widget.waiNames=["waiRole","waiState"];
dojo.widget.wai={waiRole:{name:"waiRole","namespace":"http://www.w3.org/TR/xhtml2",alias:"x2",prefix:"wairole:"},waiState:{name:"waiState","namespace":"http://www.w3.org/2005/07/aaa",alias:"aaa",prefix:""},setAttr:function(node,ns,attr,_9e2){
if(dojo.render.html.ie){
node.setAttribute(this[ns].alias+":"+attr,this[ns].prefix+_9e2);
}else{
node.setAttributeNS(this[ns]["namespace"],attr,this[ns].prefix+_9e2);
}
},getAttr:function(node,ns,attr){
if(dojo.render.html.ie){
return node.getAttribute(this[ns].alias+":"+attr);
}else{
return node.getAttributeNS(this[ns]["namespace"],attr);
}
},removeAttr:function(node,ns,attr){
var _9e9=true;
if(dojo.render.html.ie){
_9e9=node.removeAttribute(this[ns].alias+":"+attr);
}else{
node.removeAttributeNS(this[ns]["namespace"],attr);
}
return _9e9;
}};
dojo.widget.attachTemplateNodes=function(_9ea,_9eb,_9ec){
var _9ed=dojo.dom.ELEMENT_NODE;
function trim(str){
return str.replace(/^\s+|\s+$/g,"");
}
if(!_9ea){
_9ea=_9eb.domNode;
}
if(_9ea.nodeType!=_9ed){
return;
}
var _9ef=_9ea.all||_9ea.getElementsByTagName("*");
var _9f0=_9eb;
for(var x=-1;x<_9ef.length;x++){
var _9f2=(x==-1)?_9ea:_9ef[x];
var _9f3=[];
if(!_9eb.widgetsInTemplate||!_9f2.getAttribute("dojoType")){
for(var y=0;y<this.attachProperties.length;y++){
var _9f5=_9f2.getAttribute(this.attachProperties[y]);
if(_9f5){
_9f3=_9f5.split(";");
for(var z=0;z<_9f3.length;z++){
if(dojo.lang.isArray(_9eb[_9f3[z]])){
_9eb[_9f3[z]].push(_9f2);
}else{
_9eb[_9f3[z]]=_9f2;
}
}
break;
}
}
var _9f7=_9f2.getAttribute(this.eventAttachProperty);
if(_9f7){
var evts=_9f7.split(";");
for(var y=0;y<evts.length;y++){
if((!evts[y])||(!evts[y].length)){
continue;
}
var _9f9=null;
var tevt=trim(evts[y]);
if(evts[y].indexOf(":")>=0){
var _9fb=tevt.split(":");
tevt=trim(_9fb[0]);
_9f9=trim(_9fb[1]);
}
if(!_9f9){
_9f9=tevt;
}
var tf=function(){
var ntf=new String(_9f9);
return function(evt){
if(_9f0[ntf]){
_9f0[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_9f2,tevt,tf,false,true);
}
}
for(var y=0;y<_9ec.length;y++){
var _9ff=_9f2.getAttribute(_9ec[y]);
if((_9ff)&&(_9ff.length)){
var _9f9=null;
var _a00=_9ec[y].substr(4);
_9f9=trim(_9ff);
var _a01=[_9f9];
if(_9f9.indexOf(";")>=0){
_a01=dojo.lang.map(_9f9.split(";"),trim);
}
for(var z=0;z<_a01.length;z++){
if(!_a01[z].length){
continue;
}
var tf=function(){
var ntf=new String(_a01[z]);
return function(evt){
if(_9f0[ntf]){
_9f0[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_9f2,_a00,tf,false,true);
}
}
}
}
var _a04=_9f2.getAttribute(this.templateProperty);
if(_a04){
_9eb[_a04]=_9f2;
}
dojo.lang.forEach(dojo.widget.waiNames,function(name){
var wai=dojo.widget.wai[name];
var val=_9f2.getAttribute(wai.name);
if(val){
if(val.indexOf("-")==-1){
dojo.widget.wai.setAttr(_9f2,wai.name,"role",val);
}else{
var _a08=val.split("-");
dojo.widget.wai.setAttr(_9f2,wai.name,_a08[0],_a08[1]);
}
}
},this);
var _a09=_9f2.getAttribute(this.onBuildProperty);
if(_a09){
eval("var node = baseNode; var widget = targetObj; "+_a09);
}
}
};
dojo.widget.getDojoEventsFromStr=function(str){
var re=/(dojoOn([a-z]+)(\s?))=/gi;
var evts=str?str.match(re)||[]:[];
var ret=[];
var lem={};
for(var x=0;x<evts.length;x++){
if(evts[x].length<1){
continue;
}
var cm=evts[x].replace(/\s/,"");
cm=(cm.slice(0,cm.length-1));
if(!lem[cm]){
lem[cm]=true;
ret.push(cm);
}
}
return ret;
};
dojo.declare("dojo.widget.DomWidget",dojo.widget.Widget,function(){
if((arguments.length>0)&&(typeof arguments[0]=="object")){
this.create(arguments[0]);
}
},{templateNode:null,templateString:null,templateCssString:null,preventClobber:false,domNode:null,containerNode:null,widgetsInTemplate:false,addChild:function(_a11,_a12,pos,ref,_a15){
if(!this.isContainer){
dojo.debug("dojo.widget.DomWidget.addChild() attempted on non-container widget");
return null;
}else{
if(_a15==undefined){
_a15=this.children.length;
}
this.addWidgetAsDirectChild(_a11,_a12,pos,ref,_a15);
this.registerChild(_a11,_a15);
}
return _a11;
},addWidgetAsDirectChild:function(_a16,_a17,pos,ref,_a1a){
if((!this.containerNode)&&(!_a17)){
this.containerNode=this.domNode;
}
var cn=(_a17)?_a17:this.containerNode;
if(!pos){
pos="after";
}
if(!ref){
if(!cn){
cn=dojo.body();
}
ref=cn.lastChild;
}
if(!_a1a){
_a1a=0;
}
_a16.domNode.setAttribute("dojoinsertionindex",_a1a);
if(!ref){
cn.appendChild(_a16.domNode);
}else{
if(pos=="insertAtIndex"){
dojo.dom.insertAtIndex(_a16.domNode,ref.parentNode,_a1a);
}else{
if((pos=="after")&&(ref===cn.lastChild)){
cn.appendChild(_a16.domNode);
}else{
dojo.dom.insertAtPosition(_a16.domNode,cn,pos);
}
}
}
},registerChild:function(_a1c,_a1d){
_a1c.dojoInsertionIndex=_a1d;
var idx=-1;
for(var i=0;i<this.children.length;i++){
if(this.children[i].dojoInsertionIndex<=_a1d){
idx=i;
}
}
this.children.splice(idx+1,0,_a1c);
_a1c.parent=this;
_a1c.addedTo(this,idx+1);
delete dojo.widget.manager.topWidgets[_a1c.widgetId];
},removeChild:function(_a20){
dojo.dom.removeNode(_a20.domNode);
return dojo.widget.DomWidget.superclass.removeChild.call(this,_a20);
},getFragNodeRef:function(frag){
if(!frag){
return null;
}
if(!frag[this.getNamespacedType()]){
dojo.raise("Error: no frag for widget type "+this.getNamespacedType()+", id "+this.widgetId+" (maybe a widget has set it's type incorrectly)");
}
return frag[this.getNamespacedType()]["nodeRef"];
},postInitialize:function(args,frag,_a24){
var _a25=this.getFragNodeRef(frag);
if(_a24&&(_a24.snarfChildDomOutput||!_a25)){
_a24.addWidgetAsDirectChild(this,"","insertAtIndex","",args["dojoinsertionindex"],_a25);
}else{
if(_a25){
if(this.domNode&&(this.domNode!==_a25)){
var _a26=_a25.parentNode.replaceChild(this.domNode,_a25);
}
}
}
if(_a24){
_a24.registerChild(this,args.dojoinsertionindex);
}else{
dojo.widget.manager.topWidgets[this.widgetId]=this;
}
if(this.widgetsInTemplate){
var _a27=new dojo.xml.Parse();
var _a28;
var _a29=this.domNode.getElementsByTagName("*");
for(var i=0;i<_a29.length;i++){
if(_a29[i].getAttribute("dojoAttachPoint")=="subContainerWidget"){
_a28=_a29[i];
}
if(_a29[i].getAttribute("dojoType")){
_a29[i].setAttribute("_isSubWidget",true);
}
}
if(this.isContainer&&!this.containerNode){
if(_a28){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,_a28);
frag["dojoDontFollow"]=true;
}
}else{
dojo.debug("No subContainerWidget node can be found in template file for widget "+this);
}
}
var _a2c=_a27.parseElement(this.domNode,null,true);
dojo.widget.getParser().createSubComponents(_a2c,this);
var _a2d=[];
var _a2e=[this];
var w;
while((w=_a2e.pop())){
for(var i=0;i<w.children.length;i++){
var _a30=w.children[i];
if(_a30._processedSubWidgets||!_a30.extraArgs["_issubwidget"]){
continue;
}
_a2d.push(_a30);
if(_a30.isContainer){
_a2e.push(_a30);
}
}
}
for(var i=0;i<_a2d.length;i++){
var _a31=_a2d[i];
if(_a31._processedSubWidgets){
dojo.debug("This should not happen: widget._processedSubWidgets is already true!");
return;
}
_a31._processedSubWidgets=true;
if(_a31.extraArgs["dojoattachevent"]){
var evts=_a31.extraArgs["dojoattachevent"].split(";");
for(var j=0;j<evts.length;j++){
var _a34=null;
var tevt=dojo.string.trim(evts[j]);
if(tevt.indexOf(":")>=0){
var _a36=tevt.split(":");
tevt=dojo.string.trim(_a36[0]);
_a34=dojo.string.trim(_a36[1]);
}
if(!_a34){
_a34=tevt;
}
if(dojo.lang.isFunction(_a31[tevt])){
dojo.event.kwConnect({srcObj:_a31,srcFunc:tevt,targetObj:this,targetFunc:_a34});
}else{
alert(tevt+" is not a function in widget "+_a31);
}
}
}
if(_a31.extraArgs["dojoattachpoint"]){
this[_a31.extraArgs["dojoattachpoint"]]=_a31;
}
}
}
if(this.isContainer&&!frag["dojoDontFollow"]){
dojo.widget.getParser().createSubComponents(frag,this);
}
},buildRendering:function(args,frag){
var ts=dojo.widget._templateCache[this.widgetType];
if(args["templatecsspath"]){
args["templateCssPath"]=args["templatecsspath"];
}
var _a3a=args["templateCssPath"]||this.templateCssPath;
if(_a3a&&!dojo.widget._cssFiles[_a3a.toString()]){
if((!this.templateCssString)&&(_a3a)){
this.templateCssString=dojo.hostenv.getText(_a3a);
this.templateCssPath=null;
}
dojo.widget._cssFiles[_a3a.toString()]=true;
}
if((this["templateCssString"])&&(!this.templateCssString["loaded"])){
dojo.html.insertCssText(this.templateCssString,null,_a3a);
if(!this.templateCssString){
this.templateCssString="";
}
this.templateCssString.loaded=true;
}
if((!this.preventClobber)&&((this.templatePath)||(this.templateNode)||((this["templateString"])&&(this.templateString.length))||((typeof ts!="undefined")&&((ts["string"])||(ts["node"]))))){
this.buildFromTemplate(args,frag);
}else{
this.domNode=this.getFragNodeRef(frag);
}
this.fillInTemplate(args,frag);
},buildFromTemplate:function(args,frag){
var _a3d=false;
if(args["templatepath"]){
_a3d=true;
args["templatePath"]=args["templatepath"];
}
dojo.widget.fillFromTemplateCache(this,args["templatePath"],null,_a3d);
var ts=dojo.widget._templateCache[this.widgetType];
if((ts)&&(!_a3d)){
if(!this.templateString.length){
this.templateString=ts["string"];
}
if(!this.templateNode){
this.templateNode=ts["node"];
}
}
var _a3f=false;
var node=null;
var tstr=this.templateString;
if((!this.templateNode)&&(this.templateString)){
_a3f=this.templateString.match(/\$\{([^\}]+)\}/g);
if(_a3f){
var hash=this.strings||{};
for(var key in dojo.widget.defaultStrings){
if(dojo.lang.isUndefined(hash[key])){
hash[key]=dojo.widget.defaultStrings[key];
}
}
for(var i=0;i<_a3f.length;i++){
var key=_a3f[i];
key=key.substring(2,key.length-1);
var kval=(key.substring(0,5)=="this.")?dojo.lang.getObjPathValue(key.substring(5),this):hash[key];
var _a46;
if((kval)||(dojo.lang.isString(kval))){
_a46=new String((dojo.lang.isFunction(kval))?kval.call(this,key,this.templateString):kval);
while(_a46.indexOf("\"")>-1){
_a46=_a46.replace("\"","&quot;");
}
tstr=tstr.replace(_a3f[i],_a46);
}
}
}else{
this.templateNode=this.createNodesFromText(this.templateString,true)[0];
if(!_a3d){
ts.node=this.templateNode;
}
}
}
if((!this.templateNode)&&(!_a3f)){
dojo.debug("DomWidget.buildFromTemplate: could not create template");
return false;
}else{
if(!_a3f){
node=this.templateNode.cloneNode(true);
if(!node){
return false;
}
}else{
node=this.createNodesFromText(tstr,true)[0];
}
}
this.domNode=node;
this.attachTemplateNodes();
if(this.isContainer&&this.containerNode){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,this.containerNode);
}
}
},attachTemplateNodes:function(_a48,_a49){
if(!_a48){
_a48=this.domNode;
}
if(!_a49){
_a49=this;
}
return dojo.widget.attachTemplateNodes(_a48,_a49,dojo.widget.getDojoEventsFromStr(this.templateString));
},fillInTemplate:function(){
},destroyRendering:function(){
try{
delete this.domNode;
}
catch(e){
}
},cleanUp:function(){
},getContainerHeight:function(){
dojo.unimplemented("dojo.widget.DomWidget.getContainerHeight");
},getContainerWidth:function(){
dojo.unimplemented("dojo.widget.DomWidget.getContainerWidth");
},createNodesFromText:function(){
dojo.unimplemented("dojo.widget.DomWidget.createNodesFromText");
}});
dojo.provide("dojo.lfx.toggle");
dojo.lfx.toggle.plain={show:function(node,_a4b,_a4c,_a4d){
dojo.html.show(node);
if(dojo.lang.isFunction(_a4d)){
_a4d();
}
},hide:function(node,_a4f,_a50,_a51){
dojo.html.hide(node);
if(dojo.lang.isFunction(_a51)){
_a51();
}
}};
dojo.lfx.toggle.fade={show:function(node,_a53,_a54,_a55){
dojo.lfx.fadeShow(node,_a53,_a54,_a55).play();
},hide:function(node,_a57,_a58,_a59){
dojo.lfx.fadeHide(node,_a57,_a58,_a59).play();
}};
dojo.lfx.toggle.wipe={show:function(node,_a5b,_a5c,_a5d){
dojo.lfx.wipeIn(node,_a5b,_a5c,_a5d).play();
},hide:function(node,_a5f,_a60,_a61){
dojo.lfx.wipeOut(node,_a5f,_a60,_a61).play();
}};
dojo.lfx.toggle.explode={show:function(node,_a63,_a64,_a65,_a66){
dojo.lfx.explode(_a66||{x:0,y:0,width:0,height:0},node,_a63,_a64,_a65).play();
},hide:function(node,_a68,_a69,_a6a,_a6b){
dojo.lfx.implode(node,_a6b||{x:0,y:0,width:0,height:0},_a68,_a69,_a6a).play();
}};
dojo.provide("dojo.widget.HtmlWidget");
dojo.declare("dojo.widget.HtmlWidget",dojo.widget.DomWidget,{widgetType:"HtmlWidget",templateCssPath:null,templatePath:null,lang:"",toggle:"plain",toggleDuration:150,animationInProgress:false,initialize:function(args,frag){
},postMixInProperties:function(args,frag){
if(this.lang===""){
this.lang=null;
}
this.toggleObj=dojo.lfx.toggle[this.toggle.toLowerCase()]||dojo.lfx.toggle.plain;
},getContainerHeight:function(){
dojo.unimplemented("dojo.widget.HtmlWidget.getContainerHeight");
},getContainerWidth:function(){
return this.parent.domNode.offsetWidth;
},setNativeHeight:function(_a70){
var ch=this.getContainerHeight();
},createNodesFromText:function(txt,wrap){
return dojo.html.createNodesFromText(txt,wrap);
},destroyRendering:function(_a74){
try{
if(!_a74&&this.domNode){
dojo.event.browser.clean(this.domNode);
}
this.domNode.parentNode.removeChild(this.domNode);
delete this.domNode;
}
catch(e){
}
},isShowing:function(){
return dojo.html.isShowing(this.domNode);
},toggleShowing:function(){
if(this.isHidden){
this.show();
}else{
this.hide();
}
},show:function(){
this.animationInProgress=true;
this.isHidden=false;
this.toggleObj.show(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onShow),this.explodeSrc);
},onShow:function(){
this.animationInProgress=false;
this.checkSize();
},hide:function(){
this.animationInProgress=true;
this.isHidden=true;
this.toggleObj.hide(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onHide),this.explodeSrc);
},onHide:function(){
this.animationInProgress=false;
},_isResized:function(w,h){
if(!this.isShowing()){
return false;
}
var wh=dojo.html.getMarginBox(this.domNode);
var _a78=w||wh.width;
var _a79=h||wh.height;
if(this.width==_a78&&this.height==_a79){
return false;
}
this.width=_a78;
this.height=_a79;
return true;
},checkSize:function(){
if(!this._isResized()){
return;
}
this.onResized();
},resizeTo:function(w,h){
dojo.html.setMarginBox(this.domNode,{width:w,height:h});
if(this.isShowing()){
this.onResized();
}
},resizeSoon:function(){
if(this.isShowing()){
dojo.lang.setTimeout(this,this.onResized,0);
}
},onResized:function(){
dojo.lang.forEach(this.children,function(_a7c){
if(_a7c.checkSize){
_a7c.checkSize();
}
});
}});
dojo.provide("dojo.widget.*");
dojo.provide("dojo.widget.PageContainer");
dojo.widget.defineWidget("dojo.widget.PageContainer",dojo.widget.HtmlWidget,{isContainer:true,doLayout:true,templateString:"<div dojoAttachPoint='containerNode'></div>",selectedChild:"",fillInTemplate:function(args,frag){
var _a7f=this.getFragNodeRef(frag);
dojo.html.copyStyle(this.domNode,_a7f);
dojo.widget.PageContainer.superclass.fillInTemplate.apply(this,arguments);
},postCreate:function(args,frag){
if(this.children.length){
dojo.lang.forEach(this.children,this._setupChild,this);
var _a82;
if(this.selectedChild){
this.selectChild(this.selectedChild);
}else{
for(var i=0;i<this.children.length;i++){
if(this.children[i].selected){
this.selectChild(this.children[i]);
break;
}
}
if(!this.selectedChildWidget){
this.selectChild(this.children[0]);
}
}
}
},addChild:function(_a84){
dojo.widget.PageContainer.superclass.addChild.apply(this,arguments);
this._setupChild(_a84);
this.onResized();
if(!this.selectedChildWidget){
this.selectChild(_a84);
}
},_setupChild:function(page){
page.hide();
dojo.event.topic.publish(this.widgetId+"-addChild",page);
},removeChild:function(page){
dojo.widget.PageContainer.superclass.removeChild.apply(this,arguments);
if(this._beingDestroyed){
return;
}
dojo.event.topic.publish(this.widgetId+"-removeChild",page);
if(this.selectedChildWidget===page){
this.selectedChildWidget=undefined;
if(this.children.length>0){
this.selectChild(this.children[0],true);
}
}
},selectChild:function(page,_a88){
page=dojo.widget.byId(page);
this.correspondingPageButton=_a88;
if(this.selectedChildWidget){
this._hideChild(this.selectedChildWidget);
}
this.selectedChildWidget=page;
this._showChild(page);
page.isFirstChild=(page==this.children[0]);
page.isLastChild=(page==this.children[this.children.length-1]);
dojo.event.topic.publish(this.widgetId+"-selectChild",page);
},forward:function(){
var _a89=dojo.lang.find(this.children,this.selectedChildWidget);
this.selectChild(this.children[_a89+1]);
},back:function(){
var _a8a=dojo.lang.find(this.children,this.selectedChildWidget);
this.selectChild(this.children[_a8a-1]);
},onResized:function(){
if(this.doLayout&&this.selectedChildWidget){
with(this.selectedChildWidget.domNode.style){
top=dojo.html.getPixelValue(this.containerNode,"padding-top",true);
left=dojo.html.getPixelValue(this.containerNode,"padding-left",true);
}
var _a8b=dojo.html.getContentBox(this.containerNode);
this.selectedChildWidget.resizeTo(_a8b.width,_a8b.height);
}
},_showChild:function(page){
if(this.doLayout){
var _a8d=dojo.html.getContentBox(this.containerNode);
page.resizeTo(_a8d.width,_a8d.height);
}
page.selected=true;
page.show();
},_hideChild:function(page){
page.selected=false;
page.hide();
},closeChild:function(page){
var _a90=page.onClose(this,page);
if(_a90){
this.removeChild(page);
page.destroy();
}
},destroy:function(){
this._beingDestroyed=true;
dojo.event.topic.destroy(this.widgetId+"-addChild");
dojo.event.topic.destroy(this.widgetId+"-removeChild");
dojo.event.topic.destroy(this.widgetId+"-selectChild");
dojo.widget.PageContainer.superclass.destroy.apply(this,arguments);
}});
dojo.widget.defineWidget("dojo.widget.PageController",dojo.widget.HtmlWidget,{templateString:"<span wairole='tablist' dojoAttachEvent='onKey'></span>",isContainer:true,containerId:"",buttonWidget:"PageButton","class":"dojoPageController",fillInTemplate:function(){
dojo.html.addClass(this.domNode,this["class"]);
dojo.widget.wai.setAttr(this.domNode,"waiRole","role","tablist");
},postCreate:function(){
this.pane2button={};
var _a91=dojo.widget.byId(this.containerId);
if(_a91){
dojo.lang.forEach(_a91.children,this.onAddChild,this);
}
dojo.event.topic.subscribe(this.containerId+"-addChild",this,"onAddChild");
dojo.event.topic.subscribe(this.containerId+"-removeChild",this,"onRemoveChild");
dojo.event.topic.subscribe(this.containerId+"-selectChild",this,"onSelectChild");
},destroy:function(){
dojo.event.topic.unsubscribe(this.containerId+"-addChild",this,"onAddChild");
dojo.event.topic.unsubscribe(this.containerId+"-removeChild",this,"onRemoveChild");
dojo.event.topic.unsubscribe(this.containerId+"-selectChild",this,"onSelectChild");
dojo.widget.PageController.superclass.destroy.apply(this,arguments);
},onAddChild:function(page){
var _a93=dojo.widget.createWidget(this.buttonWidget,{label:page.label,closeButton:page.closable});
this.addChild(_a93);
this.domNode.appendChild(_a93.domNode);
this.pane2button[page]=_a93;
page.controlButton=_a93;
var _a94=this;
dojo.event.connect(_a93,"onClick",function(){
_a94.onButtonClick(page);
});
dojo.event.connect(_a93,"onCloseButtonClick",function(){
_a94.onCloseButtonClick(page);
});
},onRemoveChild:function(page){
if(this._currentChild==page){
this._currentChild=null;
}
var _a96=this.pane2button[page];
if(_a96){
_a96.destroy();
}
this.pane2button[page]=null;
},onSelectChild:function(page){
if(this._currentChild){
var _a98=this.pane2button[this._currentChild];
_a98.clearSelected();
}
var _a99=this.pane2button[page];
_a99.setSelected();
this._currentChild=page;
},onButtonClick:function(page){
var _a9b=dojo.widget.byId(this.containerId);
_a9b.selectChild(page,false,this);
},onCloseButtonClick:function(page){
var _a9d=dojo.widget.byId(this.containerId);
_a9d.closeChild(page);
},onKey:function(evt){
if((evt.keyCode==evt.KEY_RIGHT_ARROW)||(evt.keyCode==evt.KEY_LEFT_ARROW)){
var _a9f=0;
var next=null;
var _a9f=dojo.lang.find(this.children,this.pane2button[this._currentChild]);
if(evt.keyCode==evt.KEY_RIGHT_ARROW){
next=this.children[(_a9f+1)%this.children.length];
}else{
next=this.children[(_a9f+(this.children.length-1))%this.children.length];
}
dojo.event.browser.stopEvent(evt);
next.onClick();
}
}});
dojo.widget.defineWidget("dojo.widget.PageButton",dojo.widget.HtmlWidget,{templateString:"<span class='item'>"+"<span dojoAttachEvent='onClick' dojoAttachPoint='titleNode' class='selectButton'>${this.label}</span>"+"<span dojoAttachEvent='onClick:onCloseButtonClick' class='closeButton'>[X]</span>"+"</span>",label:"foo",closeButton:false,onClick:function(){
this.focus();
},onCloseButtonMouseOver:function(){
dojo.html.addClass(this.closeButtonNode,"closeHover");
},onCloseButtonMouseOut:function(){
dojo.html.removeClass(this.closeButtonNode,"closeHover");
},onCloseButtonClick:function(evt){
},setSelected:function(){
dojo.html.addClass(this.domNode,"current");
this.titleNode.setAttribute("tabIndex","0");
},clearSelected:function(){
dojo.html.removeClass(this.domNode,"current");
this.titleNode.setAttribute("tabIndex","-1");
},focus:function(){
if(this.titleNode.focus){
this.titleNode.focus();
}
}});
dojo.lang.extend(dojo.widget.Widget,{label:"",selected:false,closable:false,onClose:function(){
return true;
}});
dojo.provide("dojo.widget.html.layout");
dojo.widget.html.layout=function(_aa2,_aa3,_aa4){
dojo.html.addClass(_aa2,"dojoLayoutContainer");
_aa3=dojo.lang.filter(_aa3,function(_aa5,idx){
_aa5.idx=idx;
return dojo.lang.inArray(["top","bottom","left","right","client","flood"],_aa5.layoutAlign);
});
if(_aa4&&_aa4!="none"){
var rank=function(_aa8){
switch(_aa8.layoutAlign){
case "flood":
return 1;
case "left":
case "right":
return (_aa4=="left-right")?2:3;
case "top":
case "bottom":
return (_aa4=="left-right")?3:2;
default:
return 4;
}
};
_aa3.sort(function(a,b){
return (rank(a)-rank(b))||(a.idx-b.idx);
});
}
var f={top:dojo.html.getPixelValue(_aa2,"padding-top",true),left:dojo.html.getPixelValue(_aa2,"padding-left",true)};
dojo.lang.mixin(f,dojo.html.getContentBox(_aa2));
dojo.lang.forEach(_aa3,function(_aac){
var elm=_aac.domNode;
var pos=_aac.layoutAlign;
with(elm.style){
left=f.left+"px";
top=f.top+"px";
bottom="auto";
right="auto";
}
dojo.html.addClass(elm,"dojoAlign"+dojo.string.capitalize(pos));
if((pos=="top")||(pos=="bottom")){
dojo.html.setMarginBox(elm,{width:f.width});
var h=dojo.html.getMarginBox(elm).height;
f.height-=h;
if(pos=="top"){
f.top+=h;
}else{
elm.style.top=f.top+f.height+"px";
}
}else{
if(pos=="left"||pos=="right"){
var w=dojo.html.getMarginBox(elm).width;
dojo.html.setMarginBox(elm,{width:w,height:f.height});
f.width-=w;
if(pos=="left"){
f.left+=w;
}else{
elm.style.left=f.left+f.width+"px";
}
}else{
if(pos=="flood"||pos=="client"){
dojo.html.setMarginBox(elm,{width:f.width,height:f.height});
}
}
}
if(_aac.onResized){
_aac.onResized();
}
});
};
dojo.html.insertCssText(".dojoLayoutContainer{ position: relative; display: block; }\n"+"body .dojoAlignTop, body .dojoAlignBottom, body .dojoAlignLeft, body .dojoAlignRight { position: absolute; overflow: hidden; }\n"+"body .dojoAlignClient { position: absolute }\n"+".dojoAlignClient { overflow: auto; }\n");
dojo.provide("dojo.widget.TabContainer");
dojo.widget.defineWidget("dojo.widget.TabContainer",dojo.widget.PageContainer,{labelPosition:"top",closeButton:"none",templateString:null,templateString:"<div id=\"${this.widgetId}\" class=\"dojoTabContainer\">\n	<div dojoAttachPoint=\"tablistNode\"></div>\n	<div class=\"dojoTabPaneWrapper\" dojoAttachPoint=\"containerNode\" dojoAttachEvent=\"onKey\" waiRole=\"tabpanel\"></div>\n</div>\n",templateCssString:".dojoTabContainer {\n	position : relative;\n}\n\n.dojoTabPaneWrapper {\n	border : 1px solid #6290d2;\n	_zoom: 1; /* force IE6 layout mode so top border doesnt disappear */\n	display: block;\n	clear: both;\n}\n\n.dojoTabLabels-top {\n	position : relative;\n	top : 0px;\n	left : 0px;\n	overflow : visible;\n	margin-bottom : -1px;\n	width : 100%;\n	z-index: 2;	/* so the bottom of the tab label will cover up the border of dojoTabPaneWrapper */\n}\n\n.dojoTabNoLayout.dojoTabLabels-top .dojoTab {\n	margin-bottom: -1px;\n	_margin-bottom: 0px; /* IE filter so top border lines up correctly */\n}\n\n.dojoTab {\n	position : relative;\n	float : left;\n	padding-left : 9px;\n	border-bottom : 1px solid #6290d2;\n	background : url(images/tab_left.gif) no-repeat left top;\n	cursor: pointer;\n	white-space: nowrap;\n	z-index: 3;\n}\n\n.dojoTab div {\n	display : block;\n	padding : 4px 15px 4px 6px;\n	background : url(images/tab_top_right.gif) no-repeat right top;\n	color : #333;\n	font-size : 90%;\n}\n\n.dojoTab .close {\n	display : inline-block;\n	height : 12px;\n	width : 12px;\n	padding : 0 12px 0 0;\n	margin : 0 -10px 0 10px;\n	cursor : default;\n	font-size: small;\n}\n\n.dojoTab .closeImage {\n	background : url(images/tab_close.gif) no-repeat right top;\n}\n\n.dojoTab .closeHover {\n	background-image : url(images/tab_close_h.gif);\n}\n\n.dojoTab.current {\n	padding-bottom : 1px;\n	border-bottom : 0;\n	background-position : 0 -150px;\n}\n\n.dojoTab.current div {\n	padding-bottom : 5px;\n	margin-bottom : -1px;\n	background-position : 100% -150px;\n}\n\n/* bottom tabs */\n\n.dojoTabLabels-bottom {\n	position : relative;\n	bottom : 0px;\n	left : 0px;\n	overflow : visible;\n	margin-top : -1px;\n	width : 100%;\n	z-index: 2;\n}\n\n.dojoTabNoLayout.dojoTabLabels-bottom {\n	position : relative;\n}\n\n.dojoTabLabels-bottom .dojoTab {\n	border-top :  1px solid #6290d2;\n	border-bottom : 0;\n	background : url(images/tab_bot_left.gif) no-repeat left bottom;\n}\n\n.dojoTabLabels-bottom .dojoTab div {\n	background : url(images/tab_bot_right.gif) no-repeat right bottom;\n}\n\n.dojoTabLabels-bottom .dojoTab.current {\n	border-top : 0;\n	background : url(images/tab_bot_left_curr.gif) no-repeat left bottom;\n}\n\n.dojoTabLabels-bottom .dojoTab.current div {\n	padding-top : 4px;\n	background : url(images/tab_bot_right_curr.gif) no-repeat right bottom;\n}\n\n/* right-h tabs */\n\n.dojoTabLabels-right-h {\n	overflow : visible;\n	margin-left : -1px;\n	z-index: 2;\n}\n\n.dojoTabLabels-right-h .dojoTab {\n	padding-left : 0;\n	border-left :  1px solid #6290d2;\n	border-bottom : 0;\n	background : url(images/tab_bot_right.gif) no-repeat right bottom;\n	float : none;\n}\n\n.dojoTabLabels-right-h .dojoTab div {\n	padding : 4px 15px 4px 15px;\n}\n\n.dojoTabLabels-right-h .dojoTab.current {\n	border-left :  0;\n	border-bottom :  1px solid #6290d2;\n}\n\n/* left-h tabs */\n\n.dojoTabLabels-left-h {\n	overflow : visible;\n	margin-right : -1px;\n	z-index: 2;\n}\n\n.dojoTabLabels-left-h .dojoTab {\n	border-right :  1px solid #6290d2;\n	border-bottom : 0;\n	float : none;\n	background : url(images/tab_top_left.gif) no-repeat left top;\n}\n\n.dojoTabLabels-left-h .dojoTab.current {\n	border-right : 0;\n	border-bottom :  1px solid #6290d2;\n	padding-bottom : 0;\n	background : url(images/tab_top_left.gif) no-repeat 0 -150px;\n}\n\n.dojoTabLabels-left-h .dojoTab div {\n	background : 0;\n	border-bottom :  1px solid #6290d2;\n}\n",templateCssPath:dojo.uri.dojoUri("src/widget/templates/TabContainer.css"),selectedTab:"",postMixInProperties:function(){
if(this.selectedTab){
dojo.deprecated("selectedTab deprecated, use selectedChild instead, will be removed in","0.5");
this.selectedChild=this.selectedTab;
}
if(this.closeButton!="none"){
dojo.deprecated("closeButton deprecated, use closable='true' on each child instead, will be removed in","0.5");
}
dojo.widget.TabContainer.superclass.postMixInProperties.apply(this,arguments);
},fillInTemplate:function(){
this.tablist=dojo.widget.createWidget("TabController",{id:this.widgetId+"_tablist",labelPosition:this.labelPosition,doLayout:this.doLayout,containerId:this.widgetId},this.tablistNode);
dojo.widget.TabContainer.superclass.fillInTemplate.apply(this,arguments);
},postCreate:function(args,frag){
dojo.widget.TabContainer.superclass.postCreate.apply(this,arguments);
this.onResized();
},_setupChild:function(tab){
if(this.closeButton=="tab"||this.closeButton=="pane"){
tab.closable=true;
}
dojo.html.addClass(tab.domNode,"dojoTabPane");
dojo.widget.TabContainer.superclass._setupChild.apply(this,arguments);
},onResized:function(){
if(!this.doLayout){
return;
}
var _ab4=this.labelPosition.replace(/-h/,"");
var _ab5=[{domNode:this.tablist.domNode,layoutAlign:_ab4},{domNode:this.containerNode,layoutAlign:"client"}];
dojo.widget.html.layout(this.domNode,_ab5);
if(this.selectedChildWidget){
var _ab6=dojo.html.getContentBox(this.containerNode);
this.selectedChildWidget.resizeTo(_ab6.width,_ab6.height);
}
},selectTab:function(tab,_ab8){
dojo.deprecated("use selectChild() rather than selectTab(), selectTab() will be removed in","0.5");
this.selectChild(tab,_ab8);
},onKey:function(e){
if(e.keyCode==e.KEY_UP_ARROW&&e.ctrlKey){
var _aba=this.correspondingTabButton||this.selectedTabWidget.tabButton;
_aba.focus();
dojo.event.browser.stopEvent(e);
}else{
if(e.keyCode==e.KEY_DELETE&&e.altKey){
if(this.selectedChildWidget.closable){
this.closeChild(this.selectedChildWidget);
dojo.event.browser.stopEvent(e);
}
}
}
},destroy:function(){
this.tablist.destroy();
dojo.widget.TabContainer.superclass.destroy.apply(this,arguments);
}});
dojo.widget.defineWidget("dojo.widget.TabController",dojo.widget.PageController,{templateString:"<div wairole='tablist' dojoAttachEvent='onKey'></div>",labelPosition:"top",doLayout:true,"class":"",buttonWidget:"TabButton",postMixInProperties:function(){
if(!this["class"]){
this["class"]="dojoTabLabels-"+this.labelPosition+(this.doLayout?"":" dojoTabNoLayout");
}
dojo.widget.TabController.superclass.postMixInProperties.apply(this,arguments);
}});
dojo.widget.defineWidget("dojo.widget.TabButton",dojo.widget.PageButton,{templateString:"<div class='dojoTab' dojoAttachEvent='onClick'>"+"<div dojoAttachPoint='innerDiv'>"+"<span dojoAttachPoint='titleNode' tabIndex='-1' waiRole='tab'>${this.label}</span>"+"<span dojoAttachPoint='closeButtonNode' class='close closeImage' style='${this.closeButtonStyle}'"+"    dojoAttachEvent='onMouseOver:onCloseButtonMouseOver; onMouseOut:onCloseButtonMouseOut; onClick:onCloseButtonClick'></span>"+"</div>"+"</div>",postMixInProperties:function(){
this.closeButtonStyle=this.closeButton?"":"display: none";
dojo.widget.TabButton.superclass.postMixInProperties.apply(this,arguments);
},fillInTemplate:function(){
dojo.html.disableSelection(this.titleNode);
dojo.widget.TabButton.superclass.fillInTemplate.apply(this,arguments);
}});
dojo.widget.defineWidget("dojo.widget.a11y.TabButton",dojo.widget.TabButton,{imgPath:dojo.uri.dojoUri("src/widget/templates/images/tab_close.gif"),templateString:"<div class='dojoTab' dojoAttachEvent='onClick;onKey'>"+"<div dojoAttachPoint='innerDiv'>"+"<span dojoAttachPoint='titleNode' tabIndex='-1' waiRole='tab'>${this.label}</span>"+"<img class='close' src='${this.imgPath}' alt='[x]' style='${this.closeButtonStyle}'"+"    dojoAttachEvent='onClick:onCloseButtonClick'>"+"</div>"+"</div>"});
dojo.provide("dojo.widget.ContentPane");
dojo.widget.defineWidget("dojo.widget.ContentPane",dojo.widget.HtmlWidget,function(){
this._styleNodes=[];
this._onLoadStack=[];
this._onUnloadStack=[];
this._callOnUnload=false;
this._ioBindObj;
this.scriptScope;
this.bindArgs={};
},{isContainer:true,adjustPaths:true,href:"",extractContent:true,parseContent:true,cacheContent:true,preload:false,refreshOnShow:false,handler:"",executeScripts:false,scriptSeparation:true,loadingMessage:"Loading...",isLoaded:false,postCreate:function(args,frag,_abd){
if(this.handler!==""){
this.setHandler(this.handler);
}
if(this.isShowing()||this.preload){
this.loadContents();
}
},show:function(){
if(this.refreshOnShow){
this.refresh();
}else{
this.loadContents();
}
dojo.widget.ContentPane.superclass.show.call(this);
},refresh:function(){
this.isLoaded=false;
this.loadContents();
},loadContents:function(){
if(this.isLoaded){
return;
}
if(dojo.lang.isFunction(this.handler)){
this._runHandler();
}else{
if(this.href!=""){
this._downloadExternalContent(this.href,this.cacheContent&&!this.refreshOnShow);
}
}
},setUrl:function(url){
this.href=url;
this.isLoaded=false;
if(this.preload||this.isShowing()){
this.loadContents();
}
},abort:function(){
var bind=this._ioBindObj;
if(!bind||!bind.abort){
return;
}
bind.abort();
delete this._ioBindObj;
},_downloadExternalContent:function(url,_ac1){
this.abort();
this._handleDefaults(this.loadingMessage,"onDownloadStart");
var self=this;
this._ioBindObj=dojo.io.bind(this._cacheSetting({url:url,mimetype:"text/html",handler:function(type,data,xhr){
delete self._ioBindObj;
if(type=="load"){
self.onDownloadEnd.call(self,url,data);
}else{
var e={responseText:xhr.responseText,status:xhr.status,statusText:xhr.statusText,responseHeaders:xhr.getAllResponseHeaders(),text:"Error loading '"+url+"' ("+xhr.status+" "+xhr.statusText+")"};
self._handleDefaults.call(self,e,"onDownloadError");
self.onLoad();
}
}},_ac1));
},_cacheSetting:function(_ac7,_ac8){
for(var x in this.bindArgs){
if(dojo.lang.isUndefined(_ac7[x])){
_ac7[x]=this.bindArgs[x];
}
}
if(dojo.lang.isUndefined(_ac7.useCache)){
_ac7.useCache=_ac8;
}
if(dojo.lang.isUndefined(_ac7.preventCache)){
_ac7.preventCache=!_ac8;
}
if(dojo.lang.isUndefined(_ac7.mimetype)){
_ac7.mimetype="text/html";
}
return _ac7;
},onLoad:function(e){
this._runStack("_onLoadStack");
this.isLoaded=true;
},onUnLoad:function(e){
dojo.deprecated(this.widgetType+".onUnLoad, use .onUnload (lowercased load)",0.5);
},onUnload:function(e){
this._runStack("_onUnloadStack");
delete this.scriptScope;
if(this.onUnLoad!==dojo.widget.ContentPane.prototype.onUnLoad){
this.onUnLoad.apply(this,arguments);
}
},_runStack:function(_acd){
var st=this[_acd];
var err="";
var _ad0=this.scriptScope||window;
for(var i=0;i<st.length;i++){
try{
st[i].call(_ad0);
}
catch(e){
err+="\n"+st[i]+" failed: "+e.description;
}
}
this[_acd]=[];
if(err.length){
var name=(_acd=="_onLoadStack")?"addOnLoad":"addOnUnLoad";
this._handleDefaults(name+" failure\n "+err,"onExecError","debug");
}
},addOnLoad:function(obj,func){
this._pushOnStack(this._onLoadStack,obj,func);
},addOnUnload:function(obj,func){
this._pushOnStack(this._onUnloadStack,obj,func);
},addOnUnLoad:function(){
dojo.deprecated(this.widgetType+".addOnUnLoad, use addOnUnload instead. (lowercased Load)",0.5);
this.addOnUnload.apply(this,arguments);
},_pushOnStack:function(_ad7,obj,func){
if(typeof func=="undefined"){
_ad7.push(obj);
}else{
_ad7.push(function(){
obj[func]();
});
}
},destroy:function(){
this.onUnload();
dojo.widget.ContentPane.superclass.destroy.call(this);
},onExecError:function(e){
},onContentError:function(e){
},onDownloadError:function(e){
},onDownloadStart:function(e){
},onDownloadEnd:function(url,data){
data=this.splitAndFixPaths(data,url);
this.setContent(data);
},_handleDefaults:function(e,_ae1,_ae2){
if(!_ae1){
_ae1="onContentError";
}
if(dojo.lang.isString(e)){
e={text:e};
}
if(!e.text){
e.text=e.toString();
}
e.toString=function(){
return this.text;
};
if(typeof e.returnValue!="boolean"){
e.returnValue=true;
}
if(typeof e.preventDefault!="function"){
e.preventDefault=function(){
this.returnValue=false;
};
}
this[_ae1](e);
if(e.returnValue){
switch(_ae2){
case true:
case "alert":
alert(e.toString());
break;
case "debug":
dojo.debug(e.toString());
break;
default:
if(this._callOnUnload){
this.onUnload();
}
this._callOnUnload=false;
if(arguments.callee._loopStop){
dojo.debug(e.toString());
}else{
arguments.callee._loopStop=true;
this._setContent(e.toString());
}
}
}
arguments.callee._loopStop=false;
},splitAndFixPaths:function(s,url){
var _ae5=[],_ae6=[],tmp=[];
var _ae8=[],_ae9=[],attr=[],_aeb=[];
var str="",path="",fix="",_aef="",tag="",_af1="";
if(!url){
url="./";
}
if(s){
var _af2=/<title[^>]*>([\s\S]*?)<\/title>/i;
while(_ae8=_af2.exec(s)){
_ae5.push(_ae8[1]);
s=s.substring(0,_ae8.index)+s.substr(_ae8.index+_ae8[0].length);
}
if(this.adjustPaths){
var _af3=/<[a-z][a-z0-9]*[^>]*\s(?:(?:src|href|style)=[^>])+[^>]*>/i;
var _af4=/\s(src|href|style)=(['"]?)([\w()\[\]\/.,\\'"-:;#=&?\s@]+?)\2/i;
var _af5=/^(?:[#]|(?:(?:https?|ftps?|file|javascript|mailto|news):))/;
while(tag=_af3.exec(s)){
str+=s.substring(0,tag.index);
s=s.substring((tag.index+tag[0].length),s.length);
tag=tag[0];
_aef="";
while(attr=_af4.exec(tag)){
path="";
_af1=attr[3];
switch(attr[1].toLowerCase()){
case "src":
case "href":
if(_af5.exec(_af1)){
path=_af1;
}else{
path=(new dojo.uri.Uri(url,_af1).toString());
}
break;
case "style":
path=dojo.html.fixPathsInCssText(_af1,url);
break;
default:
path=_af1;
}
fix=" "+attr[1]+"="+attr[2]+path+attr[2];
_aef+=tag.substring(0,attr.index)+fix;
tag=tag.substring((attr.index+attr[0].length),tag.length);
}
str+=_aef+tag;
}
s=str+s;
}
_af2=/(?:<(style)[^>]*>([\s\S]*?)<\/style>|<link ([^>]*rel=['"]?stylesheet['"]?[^>]*)>)/i;
while(_ae8=_af2.exec(s)){
if(_ae8[1]&&_ae8[1].toLowerCase()=="style"){
_aeb.push(dojo.html.fixPathsInCssText(_ae8[2],url));
}else{
if(attr=_ae8[3].match(/href=(['"]?)([^'">]*)\1/i)){
_aeb.push({path:attr[2]});
}
}
s=s.substring(0,_ae8.index)+s.substr(_ae8.index+_ae8[0].length);
}
var _af2=/<script([^>]*)>([\s\S]*?)<\/script>/i;
var _af6=/src=(['"]?)([^"']*)\1/i;
var _af7=/.*(\bdojo\b\.js(?:\.uncompressed\.js)?)$/;
var _af8=/(?:var )?\bdjConfig\b(?:[\s]*=[\s]*\{[^}]+\}|\.[\w]*[\s]*=[\s]*[^;\n]*)?;?|dojo\.hostenv\.writeIncludes\(\s*\);?/g;
var _af9=/dojo\.(?:(?:require(?:After)?(?:If)?)|(?:widget\.(?:manager\.)?registerWidgetPackage)|(?:(?:hostenv\.)?setModulePrefix|registerModulePath)|defineNamespace)\((['"]).*?\1\)\s*;?/;
while(_ae8=_af2.exec(s)){
if(this.executeScripts&&_ae8[1]){
if(attr=_af6.exec(_ae8[1])){
if(_af7.exec(attr[2])){
dojo.debug("Security note! inhibit:"+attr[2]+" from  being loaded again.");
}else{
_ae6.push({path:attr[2]});
}
}
}
if(_ae8[2]){
var sc=_ae8[2].replace(_af8,"");
if(!sc){
continue;
}
while(tmp=_af9.exec(sc)){
_ae9.push(tmp[0]);
sc=sc.substring(0,tmp.index)+sc.substr(tmp.index+tmp[0].length);
}
if(this.executeScripts){
_ae6.push(sc);
}
}
s=s.substr(0,_ae8.index)+s.substr(_ae8.index+_ae8[0].length);
}
if(this.extractContent){
_ae8=s.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_ae8){
s=_ae8[1];
}
}
if(this.executeScripts&&this.scriptSeparation){
var _af2=/(<[a-zA-Z][a-zA-Z0-9]*\s[^>]*?\S=)((['"])[^>]*scriptScope[^>]*>)/;
var _afb=/([\s'";:\(])scriptScope(.*)/;
str="";
while(tag=_af2.exec(s)){
tmp=((tag[3]=="'")?"\"":"'");
fix="";
str+=s.substring(0,tag.index)+tag[1];
while(attr=_afb.exec(tag[2])){
tag[2]=tag[2].substring(0,attr.index)+attr[1]+"dojo.widget.byId("+tmp+this.widgetId+tmp+").scriptScope"+attr[2];
}
str+=tag[2];
s=s.substr(tag.index+tag[0].length);
}
s=str+s;
}
}
return {"xml":s,"styles":_aeb,"titles":_ae5,"requires":_ae9,"scripts":_ae6,"url":url};
},_setContent:function(cont){
this.destroyChildren();
for(var i=0;i<this._styleNodes.length;i++){
if(this._styleNodes[i]&&this._styleNodes[i].parentNode){
this._styleNodes[i].parentNode.removeChild(this._styleNodes[i]);
}
}
this._styleNodes=[];
var node=this.containerNode||this.domNode;
while(node.firstChild){
try{
dojo.event.browser.clean(node.firstChild);
}
catch(e){
}
node.removeChild(node.firstChild);
}
try{
if(typeof cont!="string"){
node.innerHTML="";
node.appendChild(cont);
}else{
node.innerHTML=cont;
}
}
catch(e){
e.text="Couldn't load content:"+e.description;
this._handleDefaults(e,"onContentError");
}
},setContent:function(data){
this.abort();
if(this._callOnUnload){
this.onUnload();
}
this._callOnUnload=true;
if(!data||dojo.html.isNode(data)){
this._setContent(data);
this.onResized();
this.onLoad();
}else{
if(typeof data.xml!="string"){
this.href="";
data=this.splitAndFixPaths(data);
}
this._setContent(data.xml);
for(var i=0;i<data.styles.length;i++){
if(data.styles[i].path){
this._styleNodes.push(dojo.html.insertCssFile(data.styles[i].path));
}else{
this._styleNodes.push(dojo.html.insertCssText(data.styles[i]));
}
}
if(this.parseContent){
for(var i=0;i<data.requires.length;i++){
try{
eval(data.requires[i]);
}
catch(e){
e.text="ContentPane: error in package loading calls, "+(e.description||e);
this._handleDefaults(e,"onContentError","debug");
}
}
}
var _b01=this;
function asyncParse(){
if(_b01.executeScripts){
_b01._executeScripts(data.scripts);
}
if(_b01.parseContent){
var node=_b01.containerNode||_b01.domNode;
var _b03=new dojo.xml.Parse();
var frag=_b03.parseElement(node,null,true);
dojo.widget.getParser().createSubComponents(frag,_b01);
}
_b01.onResized();
_b01.onLoad();
}
if(dojo.hostenv.isXDomain&&data.requires.length){
dojo.addOnLoad(asyncParse);
}else{
asyncParse();
}
}
},setHandler:function(_b05){
var fcn=dojo.lang.isFunction(_b05)?_b05:window[_b05];
if(!dojo.lang.isFunction(fcn)){
this._handleDefaults("Unable to set handler, '"+_b05+"' not a function.","onExecError",true);
return;
}
this.handler=function(){
return fcn.apply(this,arguments);
};
},_runHandler:function(){
var ret=true;
if(dojo.lang.isFunction(this.handler)){
this.handler(this,this.domNode);
ret=false;
}
this.onLoad();
return ret;
},_executeScripts:function(_b08){
var self=this;
var tmp="",code="";
for(var i=0;i<_b08.length;i++){
if(_b08[i].path){
dojo.io.bind(this._cacheSetting({"url":_b08[i].path,"load":function(type,_b0e){
dojo.lang.hitch(self,tmp=";"+_b0e);
},"error":function(type,_b10){
_b10.text=type+" downloading remote script";
self._handleDefaults.call(self,_b10,"onExecError","debug");
},"mimetype":"text/plain","sync":true},this.cacheContent));
code+=tmp;
}else{
code+=_b08[i];
}
}
try{
if(this.scriptSeparation){
delete this.scriptScope;
this.scriptScope=new (new Function("_container_",code+"; return this;"))(self);
}else{
var djg=dojo.global();
if(djg.execScript){
djg.execScript(code);
}else{
var djd=dojo.doc();
var sc=djd.createElement("script");
sc.appendChild(djd.createTextNode(code));
(this.containerNode||this.domNode).appendChild(sc);
}
}
}
catch(e){
e.text="Error running scripts from content:\n"+e.description;
this._handleDefaults(e,"onExecError","debug");
}
}});
dojo.provide("dojo.widget.LinkPane");
dojo.widget.defineWidget("dojo.widget.LinkPane",dojo.widget.ContentPane,{templateString:"<div class=\"dojoLinkPane\"></div>",fillInTemplate:function(args,frag){
var _b16=this.getFragNodeRef(frag);
this.label+=_b16.innerHTML;
var _b16=this.getFragNodeRef(frag);
dojo.html.copyStyle(this.domNode,_b16);
}});
dojo.provide("dojo.math");
dojo.math.degToRad=function(x){
return (x*Math.PI)/180;
};
dojo.math.radToDeg=function(x){
return (x*180)/Math.PI;
};
dojo.math.factorial=function(n){
if(n<1){
return 0;
}
var _b1a=1;
for(var i=1;i<=n;i++){
_b1a*=i;
}
return _b1a;
};
dojo.math.permutations=function(n,k){
if(n==0||k==0){
return 1;
}
return (dojo.math.factorial(n)/dojo.math.factorial(n-k));
};
dojo.math.combinations=function(n,r){
if(n==0||r==0){
return 1;
}
return (dojo.math.factorial(n)/(dojo.math.factorial(n-r)*dojo.math.factorial(r)));
};
dojo.math.bernstein=function(t,n,i){
return (dojo.math.combinations(n,i)*Math.pow(t,i)*Math.pow(1-t,n-i));
};
dojo.math.gaussianRandom=function(){
var k=2;
do{
var i=2*Math.random()-1;
var j=2*Math.random()-1;
k=i*i+j*j;
}while(k>=1);
k=Math.sqrt((-2*Math.log(k))/k);
return i*k;
};
dojo.math.mean=function(){
var _b26=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0;
for(var i=0;i<_b26.length;i++){
mean+=_b26[i];
}
return mean/_b26.length;
};
dojo.math.round=function(_b29,_b2a){
if(!_b2a){
var _b2b=1;
}else{
var _b2b=Math.pow(10,_b2a);
}
return Math.round(_b29*_b2b)/_b2b;
};
dojo.math.sd=dojo.math.standardDeviation=function(){
var _b2c=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
return Math.sqrt(dojo.math.variance(_b2c));
};
dojo.math.variance=function(){
var _b2d=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0,_b2f=0;
for(var i=0;i<_b2d.length;i++){
mean+=_b2d[i];
_b2f+=Math.pow(_b2d[i],2);
}
return (_b2f/_b2d.length)-Math.pow(mean/_b2d.length,2);
};
dojo.math.range=function(a,b,step){
if(arguments.length<2){
b=a;
a=0;
}
if(arguments.length<3){
step=1;
}
var _b34=[];
if(step>0){
for(var i=a;i<b;i+=step){
_b34.push(i);
}
}else{
if(step<0){
for(var i=a;i>b;i+=step){
_b34.push(i);
}
}else{
throw new Error("dojo.math.range: step must be non-zero");
}
}
return _b34;
};
dojo.provide("dojo.math.curves");
dojo.math.curves={Line:function(_b36,end){
this.start=_b36;
this.end=end;
this.dimensions=_b36.length;
for(var i=0;i<_b36.length;i++){
_b36[i]=Number(_b36[i]);
}
for(var i=0;i<end.length;i++){
end[i]=Number(end[i]);
}
this.getValue=function(n){
var _b3a=new Array(this.dimensions);
for(var i=0;i<this.dimensions;i++){
_b3a[i]=((this.end[i]-this.start[i])*n)+this.start[i];
}
return _b3a;
};
return this;
},Bezier:function(pnts){
this.getValue=function(step){
if(step>=1){
return this.p[this.p.length-1];
}
if(step<=0){
return this.p[0];
}
var _b3e=new Array(this.p[0].length);
for(var k=0;j<this.p[0].length;k++){
_b3e[k]=0;
}
for(var j=0;j<this.p[0].length;j++){
var C=0;
var D=0;
for(var i=0;i<this.p.length;i++){
C+=this.p[i][j]*this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,i);
}
for(var l=0;l<this.p.length;l++){
D+=this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,l);
}
_b3e[j]=C/D;
}
return _b3e;
};
this.p=pnts;
return this;
},CatmullRom:function(pnts,c){
this.getValue=function(step){
var _b48=step*(this.p.length-1);
var node=Math.floor(_b48);
var _b4a=_b48-node;
var i0=node-1;
if(i0<0){
i0=0;
}
var i=node;
var i1=node+1;
if(i1>=this.p.length){
i1=this.p.length-1;
}
var i2=node+2;
if(i2>=this.p.length){
i2=this.p.length-1;
}
var u=_b4a;
var u2=_b4a*_b4a;
var u3=_b4a*_b4a*_b4a;
var _b52=new Array(this.p[0].length);
for(var k=0;k<this.p[0].length;k++){
var x1=(-this.c*this.p[i0][k])+((2-this.c)*this.p[i][k])+((this.c-2)*this.p[i1][k])+(this.c*this.p[i2][k]);
var x2=(2*this.c*this.p[i0][k])+((this.c-3)*this.p[i][k])+((3-2*this.c)*this.p[i1][k])+(-this.c*this.p[i2][k]);
var x3=(-this.c*this.p[i0][k])+(this.c*this.p[i1][k]);
var x4=this.p[i][k];
_b52[k]=x1*u3+x2*u2+x3*u+x4;
}
return _b52;
};
if(!c){
this.c=0.7;
}else{
this.c=c;
}
this.p=pnts;
return this;
},Arc:function(_b58,end,ccw){
var _b5b=dojo.math.points.midpoint(_b58,end);
var _b5c=dojo.math.points.translate(dojo.math.points.invert(_b5b),_b58);
var rad=Math.sqrt(Math.pow(_b5c[0],2)+Math.pow(_b5c[1],2));
var _b5e=dojo.math.radToDeg(Math.atan(_b5c[1]/_b5c[0]));
if(_b5c[0]<0){
_b5e-=90;
}else{
_b5e+=90;
}
dojo.math.curves.CenteredArc.call(this,_b5b,rad,_b5e,_b5e+(ccw?-180:180));
},CenteredArc:function(_b5f,_b60,_b61,end){
this.center=_b5f;
this.radius=_b60;
this.start=_b61||0;
this.end=end;
this.getValue=function(n){
var _b64=new Array(2);
var _b65=dojo.math.degToRad(this.start+((this.end-this.start)*n));
_b64[0]=this.center[0]+this.radius*Math.sin(_b65);
_b64[1]=this.center[1]-this.radius*Math.cos(_b65);
return _b64;
};
return this;
},Circle:function(_b66,_b67){
dojo.math.curves.CenteredArc.call(this,_b66,_b67,0,360);
return this;
},Path:function(){
var _b68=[];
var _b69=[];
var _b6a=[];
var _b6b=0;
this.add=function(_b6c,_b6d){
if(_b6d<0){
dojo.raise("dojo.math.curves.Path.add: weight cannot be less than 0");
}
_b68.push(_b6c);
_b69.push(_b6d);
_b6b+=_b6d;
computeRanges();
};
this.remove=function(_b6e){
for(var i=0;i<_b68.length;i++){
if(_b68[i]==_b6e){
_b68.splice(i,1);
_b6b-=_b69.splice(i,1)[0];
break;
}
}
computeRanges();
};
this.removeAll=function(){
_b68=[];
_b69=[];
_b6b=0;
};
this.getValue=function(n){
var _b71=false,_b72=0;
for(var i=0;i<_b6a.length;i++){
var r=_b6a[i];
if(n>=r[0]&&n<r[1]){
var subN=(n-r[0])/r[2];
_b72=_b68[i].getValue(subN);
_b71=true;
break;
}
}
if(!_b71){
_b72=_b68[_b68.length-1].getValue(1);
}
for(var j=0;j<i;j++){
_b72=dojo.math.points.translate(_b72,_b68[j].getValue(1));
}
return _b72;
};
function computeRanges(){
var _b77=0;
for(var i=0;i<_b69.length;i++){
var end=_b77+_b69[i]/_b6b;
var len=end-_b77;
_b6a[i]=[_b77,end,len];
_b77=end;
}
}
return this;
}};
dojo.provide("dojo.math.points");
dojo.math.points={translate:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.translate: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=a[i]+b[i];
}
return c;
},midpoint:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.midpoint: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=(a[i]+b[i])/2;
}
return c;
},invert:function(a){
var b=new Array(a.length);
for(var i=0;i<a.length;i++){
b[i]=-a[i];
}
return b;
},distance:function(a,b){
return Math.sqrt(Math.pow(b[0]-a[0],2)+Math.pow(b[1]-a[1],2));
}};
dojo.provide("dojo.math.*");
dojo.provide("struts.widget.Bind");
dojo.widget.defineWidget("struts.widget.Bind",dojo.widget.HtmlWidget,{widgetType:"Bind",executeScripts:false,targets:"",targetsArray:null,href:"",handler:"",loadingText:"Loading...",errorText:"",showError:true,showLoading:true,listenTopics:"",notifyTopics:"",notifyTopicsArray:null,beforeLoading:"",afterLoading:"",formId:"",formFilter:"",formNode:null,event:"",indicator:"",parseContent:true,postCreate:function(){
var self=this;
if(!dojo.string.isBlank(this.listenTopics)){
this.log("Listening to "+this.listenTopics+" to refresh");
var _b89=this.listenTopics.split(",");
if(_b89){
dojo.lang.forEach(_b89,function(_b8a){
dojo.event.topic.subscribe(_b8a,self,"reloadContents");
});
}
}
if(!dojo.string.isBlank(this.notifyTopics)){
this.notifyTopicsArray=this.notifyTopics.split(",");
}
if(!dojo.string.isBlank(this.targets)){
this.targetsArray=this.targets.split(",");
}
if(!dojo.string.isBlank(this.event)){
dojo.event.connect(this.domNode,this.event,function(evt){
evt.preventDefault();
evt.stopPropagation();
self.reloadContents();
});
}
if(dojo.string.isBlank(this.href)&&dojo.string.isBlank(this.formId)){
this.formNode=dojo.dom.getFirstAncestorByTag(this.domNode,"form");
}else{
this.formNode=dojo.byId(this.formId);
}
if(this.formNode&&dojo.string.isBlank(this.href)){
this.href=this.formNode.action;
}
if(!dojo.string.isBlank(this.formId)){
this.formNode=dojo.byId(this.formId);
}
},log:function(text){
dojo.debug("["+(this.widgetId?this.widgetId:"unknown")+"] "+text);
},setContent:function(text){
if(this.targetsArray){
var self=this;
var _b8f=new dojo.xml.Parse();
dojo.lang.forEach(this.targetsArray,function(_b90){
var node=dojo.byId(_b90);
node.innerHTML=text;
if(self.parseContent&&text!=self.loadingText){
var frag=_b8f.parseElement(node,null,true);
dojo.widget.getParser().createSubComponents(frag,dojo.widget.byId(_b90));
}
});
}
},bindHandler:function(type,data,e){
dojo.html.hide(this.indicator);
if(!dojo.string.isBlank(this.afterLoading)){
this.log("Executing "+this.afterLoading);
eval(this.afterLoading);
}
this.notify(data,type,e);
if(type=="load"){
if(this.executeScripts){
var _b96=this.parse(data);
if(_b96.scripts&&_b96.scripts.length>0){
var _b97="";
for(var i=0;i<_b96.scripts.length;i++){
_b97+=_b96.scripts[i];
}
(new Function("_container_",_b97+"; return this;"))(this);
}
this.setContent(_b96.text);
}else{
this.setContent(data);
}
}else{
if(this.showError){
var _b99=dojo.string.isBlank(this.errorText)?e.message:this.errorText;
this.setContent(_b99);
}
}
},notify:function(data,type,e){
if(this.notifyTopicsArray){
var self=this;
dojo.lang.forEach(this.notifyTopicsArray,function(_b9e){
try{
dojo.event.topic.publish(_b9e,data,type,e);
}
catch(ex){
self.log(ex);
}
});
}
},onDownloadStart:function(_b9f){
if(!dojo.string.isBlank(this.beforeLoading)){
var data=null;
var type=null;
eval(this.beforeLoading);
}
if(this.showLoading&&!dojo.string.isBlank(this.loadingText)){
_b9f.text=this.loadingText;
}
},reloadContents:function(evt){
if(!dojo.string.isBlank(this.handler)){
this.log("Invoking handler: "+this.handler);
window[this.handler](this,this.domNode);
}else{
if(!dojo.string.isBlank(this.beforeLoading)){
this.log("Executing "+this.beforeLoading);
var data=null;
var type=null;
eval(this.beforeLoading);
}
try{
var self=this;
var _ba6={cancel:false};
this.notify(this.widgetId,"before",_ba6);
if(_ba6.cancel){
this.log("Request canceled");
return;
}
if(dojo.string.isBlank(this.href)){
return;
}
if(this.formNode&&this.formNode.onsubmit!=null){
var _ba7=this.formNode.onsubmit.call(evt);
if(_ba7!=null&&!_ba7){
this.log("Request canceled by 'onsubmit' of the form");
return;
}
}
dojo.html.show(this.indicator);
if(this.showLoading){
this.setContent(this.loadingText);
}
dojo.io.bind({url:this.href,useCache:false,preventCache:true,formNode:self.formNode,formFilter:window[self.formFilter],handler:function(type,data,e){
dojo.lang.hitch(self,"bindHandler")(type,data,e);
},mimetype:"text/html"});
}
catch(ex){
var _bab=dojo.string.isBlank(this.errorText)?ex:this.errorText;
this.setContent(_bab);
}
}
},parse:function(s){
this.log("Parsing: "+s);
var _bad=[];
var tmp=[];
var _baf=[];
while(_bad){
_bad=s.match(/<script([^>]*)>([\s\S]*?)<\/script>/i);
if(!_bad){
break;
}
if(_bad[1]){
attr=_bad[1].match(/src=(['"]?)([^"']*)\1/i);
if(attr){
var tmp2=attr[2].search(/.*(\bdojo\b(?:\.uncompressed)?\.js)$/);
if(tmp2>-1){
this.log("Security note! inhibit:"+attr[2]+" from  beeing loaded again.");
}
}
}
if(_bad[2]){
var sc=_bad[2].replace(/(?:var )?\bdjConfig\b(?:[\s]*=[\s]*\{[^}]+\}|\.[\w]*[\s]*=[\s]*[^;\n]*)?;?|dojo\.hostenv\.writeIncludes\(\s*\);?/g,"");
if(!sc){
continue;
}
tmp=[];
while(tmp){
tmp=sc.match(/dojo\.(?:(?:require(?:After)?(?:If)?)|(?:widget\.(?:manager\.)?registerWidgetPackage)|(?:(?:hostenv\.)?setModulePrefix))\((['"]).*?\1\)\s*;?/);
if(!tmp){
break;
}
sc=sc.replace(tmp[0],"");
}
_baf.push(sc);
}
s=s.replace(/<script[^>]*>[\s\S]*?<\/script>/i,"");
}
return {text:s,scripts:_baf};
}});
dojo.provide("dojo.lang.timing.Timer");
dojo.lang.timing.Timer=function(_bb2){
this.timer=null;
this.isRunning=false;
this.interval=_bb2;
this.onStart=null;
this.onStop=null;
};
dojo.extend(dojo.lang.timing.Timer,{onTick:function(){
},setInterval:function(_bb3){
if(this.isRunning){
dj_global.clearInterval(this.timer);
}
this.interval=_bb3;
if(this.isRunning){
this.timer=dj_global.setInterval(dojo.lang.hitch(this,"onTick"),this.interval);
}
},start:function(){
if(typeof this.onStart=="function"){
this.onStart();
}
this.isRunning=true;
this.timer=dj_global.setInterval(dojo.lang.hitch(this,"onTick"),this.interval);
},stop:function(){
if(typeof this.onStop=="function"){
this.onStop();
}
this.isRunning=false;
dj_global.clearInterval(this.timer);
}});
dojo.provide("struts.widget.BindDiv");
dojo.widget.defineWidget("struts.widget.BindDiv",dojo.widget.ContentPane,{widgetType:"BindDiv",href:"",extractContent:false,parseContent:false,cacheContent:false,refreshOnShow:false,executeScripts:false,updateFreq:0,delay:0,autoStart:true,timer:null,loadingText:"Loading...",showLoading:true,errorText:"",showError:true,listenTopics:"",notifyTopics:"",notifyTopicsArray:null,stopTimerListenTopics:"",startTimerListenTopics:"",beforeLoading:"",afterLoading:"",formId:"",formFilter:"",firstTime:true,indicator:"",parseContent:true,onDownloadStart:function(_bb4){
if(!dojo.string.isBlank(this.beforeLoading)){
this.log("Executing "+this.beforeLoading);
var _bb5=eval(this.beforeLoading);
if(_bb5!==null&&!_bb5){
return;
}
}
if(!this.showLoading){
_bb4.returnValue=false;
return;
}
if(this.showLoading&&!dojo.string.isBlank(this.loadingText)){
_bb4.text=this.loadingText;
}
},onDownloadError:function(_bb6){
this.onError(_bb6);
},onContentError:function(_bb7){
this.onError(_bb7);
},onExecError:function(_bb8){
this.onError(_bb8);
},onError:function(_bb9){
if(this.showError){
if(!dojo.string.isBlank(this.errorText)){
_bb9.text=this.errorText;
}
}else{
_bb9.text="";
}
},notify:function(data,type,e){
if(this.notifyTopicsArray){
var self=this;
dojo.lang.forEach(this.notifyTopicsArray,function(_bbe){
try{
dojo.event.topic.publish(_bbe,data,type,e);
}
catch(ex){
self.log(ex);
}
});
}
},postCreate:function(args,frag){
struts.widget.BindDiv.superclass.postCreate.apply(this);
var self=this;
var _bc2=function(){
dojo.lang.hitch(self,"refresh")();
};
var _bc3=function(){
dojo.lang.hitch(self,"startTimer")();
};
if(this.updateFreq>0){
this.timer=new dojo.lang.timing.Timer(this.updateFreq);
this.timer.onTick=_bc2;
if(this.autoStart){
if(this.delay>0){
dojo.lang.setTimeout(this.delay,_bc3);
}else{
this.startTimer();
}
}
}else{
if(this.delay>0){
dojo.lang.setTimeout(this.delay,_bc2);
}
}
if(this.autoStart){
if(this.delay>0){
if(this.updateFreq>0){
dojo.lang.setTimeout(this.delay,_bc3);
}else{
dojo.lang.setTimeout(this.delay,_bc2);
}
}
}
if(!dojo.string.isBlank(this.listenTopics)){
this.log("Listening to "+this.listenTopics+" to refresh");
var _bc4=this.listenTopics.split(",");
if(_bc4){
dojo.lang.forEach(_bc4,function(_bc5){
dojo.event.topic.subscribe(_bc5,self,"refresh");
});
}
}
if(!dojo.string.isBlank(this.notifyTopics)){
this.notifyTopicsArray=this.notifyTopics.split(",");
}
if(!dojo.string.isBlank(this.stopTimerListenTopics)){
this.log("Listening to "+this.stopTimerListenTopics+" to stop timer");
var _bc6=this.stopTimerListenTopics.split(",");
if(_bc6){
dojo.lang.forEach(_bc6,function(_bc7){
dojo.event.topic.subscribe(_bc7,self,"stopTimer");
});
}
}
if(!dojo.string.isBlank(this.startTimerListenTopics)){
this.log("Listening to "+this.stopTimerListenTopics+" to start timer");
var _bc8=this.startTimerListenTopics.split(",");
if(_bc8){
dojo.lang.forEach(_bc8,function(_bc9){
dojo.event.topic.subscribe(_bc9,self,"startTimer");
});
}
}
},_downloadExternalContent:function(url,_bcb){
if(this.firstTime){
this.firstTime=false;
if(this.delay>0){
return;
}
}
var _bcc={cancel:false};
this.notify(this.widgetId,"before",_bcc);
if(_bcc.cancel){
return;
}
dojo.html.show(this.indicator);
this._handleDefaults("Loading...","onDownloadStart");
var self=this;
dojo.io.bind({url:url,useCache:_bcb,preventCache:!_bcb,mimetype:"text/html",formNode:dojo.byId(self.formId),formFilter:window[self.formFilter],handler:function(type,data,e){
dojo.html.hide(self.indicator);
if(!dojo.string.isBlank(self.afterLoading)){
self.log("Executing "+self.afterLoading);
eval(self.afterLoading);
}
self.notify(data,type,null);
if(type=="load"){
self.onDownloadEnd.call(self,url,data);
}else{
self._handleDefaults.call(self,"Error loading '"+url+"' ("+e.status+" "+e.statusText+")","onDownloadError");
self.onLoad();
}
}});
},log:function(text){
dojo.debug("["+this.widgetId+"] "+text);
},stopTimer:function(){
if(this.timer&&this.timer.isRunning){
this.log("stopping timer");
this.timer.stop();
}
},startTimer:function(){
if(this.timer&&!this.timer.isRunning){
this.log("starting timer with update interval "+this.updateFreq);
this.timer.start();
}
},splitAndFixPaths:function(s,url){
var _bd4=[],_bd5=[],tmp=[];
var _bd7=[],_bd8=[],attr=[],_bda=[];
var str="",path="",fix="",_bde="",tag="",_be0="";
if(!url){
url="./";
}
if(s){
var _be1=/<title[^>]*>([\s\S]*?)<\/title>/i;
while(_bd7=_be1.exec(s)){
_bd4.push(_bd7[1]);
s=s.substring(0,_bd7.index)+s.substr(_bd7.index+_bd7[0].length);
}
if(this.adjustPaths){
var _be2=/<[a-z][a-z0-9]*[^>]*\s(?:(?:src|href|style)=[^>])+[^>]*>/i;
var _be3=/\s(src|href|style)=(['"]?)([\w()\[\]\/.,\\'"-:;#=&?\s@!]+?)\2/i;
var _be4=/^(?:[#]|(?:(?:https?|ftps?|file|javascript|mailto|news):))/;
while(tag=_be2.exec(s)){
str+=s.substring(0,tag.index);
s=s.substring((tag.index+tag[0].length),s.length);
tag=tag[0];
_bde="";
while(attr=_be3.exec(tag)){
path="";
_be0=attr[3];
switch(attr[1].toLowerCase()){
case "src":
case "href":
if(_be4.exec(_be0)){
path=_be0;
}else{
path=(new dojo.uri.Uri(url,_be0).toString());
}
break;
case "style":
path=dojo.html.fixPathsInCssText(_be0,url);
break;
default:
path=_be0;
}
fix=" "+attr[1]+"="+attr[2]+path+attr[2];
_bde+=tag.substring(0,attr.index)+fix;
tag=tag.substring((attr.index+attr[0].length),tag.length);
}
str+=_bde+tag;
}
s=str+s;
}
_be1=/(?:<(style)[^>]*>([\s\S]*?)<\/style>|<link ([^>]*rel=['"]?stylesheet['"]?[^>]*)>)/i;
while(_bd7=_be1.exec(s)){
if(_bd7[1]&&_bd7[1].toLowerCase()=="style"){
_bda.push(dojo.html.fixPathsInCssText(_bd7[2],url));
}else{
if(attr=_bd7[3].match(/href=(['"]?)([^'">]*)\1/i)){
_bda.push({path:attr[2]});
}
}
s=s.substring(0,_bd7.index)+s.substr(_bd7.index+_bd7[0].length);
}
var _be1=/<script([^>]*)>([\s\S]*?)<\/script>/i;
var _be5=/src=(['"]?)([^"']*)\1/i;
var _be6=/.*(\bdojo\b\.js(?:\.uncompressed\.js)?)$/;
var _be7=/(?:var )?\bdjConfig\b(?:[\s]*=[\s]*\{[^}]+\}|\.[\w]*[\s]*=[\s]*[^;\n]*)?;?|dojo\.hostenv\.writeIncludes\(\s*\);?/g;
var _be8=/dojo\.(?:(?:require(?:After)?(?:If)?)|(?:widget\.(?:manager\.)?registerWidgetPackage)|(?:(?:hostenv\.)?setModulePrefix|registerModulePath)|defineNamespace)\((['"]).*?\1\)\s*;?/;
while(_bd7=_be1.exec(s)){
if(this.executeScripts&&_bd7[1]){
if(attr=_be5.exec(_bd7[1])){
if(_be6.exec(attr[2])){
dojo.debug("Security note! inhibit:"+attr[2]+" from  being loaded again.");
}else{
_bd5.push({path:attr[2]});
}
}
}
if(_bd7[2]){
var sc=_bd7[2].replace(_be7,"");
if(!sc){
continue;
}
while(tmp=_be8.exec(sc)){
_bd8.push(tmp[0]);
sc=sc.substring(0,tmp.index)+sc.substr(tmp.index+tmp[0].length);
}
if(this.executeScripts){
_bd5.push(sc);
}
}
s=s.substr(0,_bd7.index)+s.substr(_bd7.index+_bd7[0].length);
}
if(this.extractContent){
_bd7=s.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_bd7){
s=_bd7[1];
}
}
if(this.executeScripts&&this.scriptSeparation){
var _be1=/(<[a-zA-Z][a-zA-Z0-9]*\s[^>]*?\S=)((['"])[^>]*scriptScope[^>]*>)/;
var _bea=/([\s'";:\(])scriptScope(.*)/;
str="";
while(tag=_be1.exec(s)){
tmp=((tag[3]=="'")?"\"":"'");
fix="";
str+=s.substring(0,tag.index)+tag[1];
while(attr=_bea.exec(tag[2])){
tag[2]=tag[2].substring(0,attr.index)+attr[1]+"dojo.widget.byId("+tmp+this.widgetId+tmp+").scriptScope"+attr[2];
}
str+=tag[2];
s=s.substr(tag.index+tag[0].length);
}
s=str+s;
}
}
return {"xml":s,"styles":_bda,"titles":_bd4,"requires":_bd8,"scripts":_bd5,"url":url};
}});
dojo.provide("struts.widget.BindAnchor");
dojo.widget.defineWidget("struts.widget.BindAnchor",struts.widget.Bind,{widgetType:"BindAnchor",event:"onclick",postCreate:function(){
struts.widget.BindAnchor.superclass.postCreate.apply(this);
this.domNode.href="#";
}});
dojo.provide("dojo.widget.html.stabile");
dojo.widget.html.stabile={_sqQuotables:new RegExp("([\\\\'])","g"),_depth:0,_recur:false,depthLimit:2};
dojo.widget.html.stabile.getState=function(id){
dojo.widget.html.stabile.setup();
return dojo.widget.html.stabile.widgetState[id];
};
dojo.widget.html.stabile.setState=function(id,_bed,_bee){
dojo.widget.html.stabile.setup();
dojo.widget.html.stabile.widgetState[id]=_bed;
if(_bee){
dojo.widget.html.stabile.commit(dojo.widget.html.stabile.widgetState);
}
};
dojo.widget.html.stabile.setup=function(){
if(!dojo.widget.html.stabile.widgetState){
var text=dojo.widget.html.stabile.getStorage().value;
dojo.widget.html.stabile.widgetState=text?dj_eval("("+text+")"):{};
}
};
dojo.widget.html.stabile.commit=function(_bf0){
dojo.widget.html.stabile.getStorage().value=dojo.widget.html.stabile.description(_bf0);
};
dojo.widget.html.stabile.description=function(v,_bf2){
var _bf3=dojo.widget.html.stabile._depth;
var _bf4=function(){
return this.description(this,true);
};
try{
if(v===void (0)){
return "undefined";
}
if(v===null){
return "null";
}
if(typeof (v)=="boolean"||typeof (v)=="number"||v instanceof Boolean||v instanceof Number){
return v.toString();
}
if(typeof (v)=="string"||v instanceof String){
var v1=v.replace(dojo.widget.html.stabile._sqQuotables,"\\$1");
v1=v1.replace(/\n/g,"\\n");
v1=v1.replace(/\r/g,"\\r");
return "'"+v1+"'";
}
if(v instanceof Date){
return "new Date("+d.getFullYear+","+d.getMonth()+","+d.getDate()+")";
}
var d;
if(v instanceof Array||v.push){
if(_bf3>=dojo.widget.html.stabile.depthLimit){
return "[ ... ]";
}
d="[";
var _bf7=true;
dojo.widget.html.stabile._depth++;
for(var i=0;i<v.length;i++){
if(_bf7){
_bf7=false;
}else{
d+=",";
}
d+=arguments.callee(v[i],_bf2);
}
return d+"]";
}
if(v.constructor==Object||v.toString==_bf4){
if(_bf3>=dojo.widget.html.stabile.depthLimit){
return "{ ... }";
}
if(typeof (v.hasOwnProperty)!="function"&&v.prototype){
throw new Error("description: "+v+" not supported by script engine");
}
var _bf7=true;
d="{";
dojo.widget.html.stabile._depth++;
for(var key in v){
if(v[key]==void (0)||typeof (v[key])=="function"){
continue;
}
if(_bf7){
_bf7=false;
}else{
d+=", ";
}
var kd=key;
if(!kd.match(/^[a-zA-Z_][a-zA-Z0-9_]*$/)){
kd=arguments.callee(key,_bf2);
}
d+=kd+": "+arguments.callee(v[key],_bf2);
}
return d+"}";
}
if(_bf2){
if(dojo.widget.html.stabile._recur){
var _bfb=Object.prototype.toString;
return _bfb.apply(v,[]);
}else{
dojo.widget.html.stabile._recur=true;
return v.toString();
}
}else{
throw new Error("Unknown type: "+v);
return "'unknown'";
}
}
finally{
dojo.widget.html.stabile._depth=_bf3;
}
};
dojo.widget.html.stabile.getStorage=function(){
if(dojo.widget.html.stabile.dataField){
return dojo.widget.html.stabile.dataField;
}
var form=document.forms._dojo_form;
return dojo.widget.html.stabile.dataField=form?form.stabile:{value:""};
};
dojo.provide("dojo.widget.PopupContainer");
dojo.declare("dojo.widget.PopupContainerBase",null,function(){
this.queueOnAnimationFinish=[];
},{isContainer:true,templateString:"<div dojoAttachPoint=\"containerNode\" style=\"display:none;position:absolute;\" class=\"dojoPopupContainer\" ></div>",isShowingNow:false,currentSubpopup:null,beginZIndex:1000,parentPopup:null,parent:null,popupIndex:0,aroundBox:dojo.html.boxSizing.BORDER_BOX,openedForWindow:null,processKey:function(evt){
return false;
},applyPopupBasicStyle:function(){
with(this.domNode.style){
display="none";
position="absolute";
}
},aboutToShow:function(){
},open:function(x,y,_c00,_c01,_c02,_c03){
if(this.isShowingNow){
return;
}
this.aboutToShow();
if(this.animationInProgress){
this.queueOnAnimationFinish.push(this.open,arguments);
return;
}
this.parent=_c00;
var _c04=false,node,_c06;
if(typeof x=="object"){
node=x;
_c06=_c01;
_c01=_c00;
_c00=y;
_c04=true;
}
dojo.body().appendChild(this.domNode);
_c01=_c01||_c00["domNode"]||[];
var _c07=null;
this.isTopLevel=true;
while(_c00){
if(_c00!==this&&(_c00.setOpenedSubpopup!=undefined&&_c00.applyPopupBasicStyle!=undefined)){
_c07=_c00;
this.isTopLevel=false;
_c07.setOpenedSubpopup(this);
break;
}
_c00=_c00.parent;
}
this.parentPopup=_c07;
this.popupIndex=_c07?_c07.popupIndex+1:1;
if(this.isTopLevel){
var _c08=dojo.html.isNode(_c01)?_c01:null;
dojo.widget.PopupManager.opened(this,_c08);
}
if(this.isTopLevel&&!dojo.withGlobal(this.openedForWindow||dojo.global(),dojo.html.selection.isCollapsed)){
this._bookmark=dojo.withGlobal(this.openedForWindow||dojo.global(),dojo.html.selection.getBookmark);
}else{
this._bookmark=null;
}
if(_c01 instanceof Array){
_c01={left:_c01[0],top:_c01[1],width:0,height:0};
}
with(this.domNode.style){
display="";
zIndex=this.beginZIndex+this.popupIndex;
}
if(_c04){
this.move(node,_c03,_c06);
}else{
this.move(x,y,_c03,_c02);
}
this.domNode.style.display="none";
this.explodeSrc=_c01;
this.show();
this.isShowingNow=true;
},move:function(x,y,_c0b,_c0c){
var _c0d=(typeof x=="object");
if(_c0d){
var _c0e=_c0b;
var node=x;
_c0b=y;
if(!_c0e){
_c0e={"BL":"TL","TL":"BL"};
}
dojo.html.placeOnScreenAroundElement(this.domNode,node,_c0b,this.aroundBox,_c0e);
}else{
if(!_c0c){
_c0c="TL,TR,BL,BR";
}
dojo.html.placeOnScreen(this.domNode,x,y,_c0b,true,_c0c);
}
},close:function(_c10){
if(_c10){
this.domNode.style.display="none";
}
if(this.animationInProgress){
this.queueOnAnimationFinish.push(this.close,[]);
return;
}
this.closeSubpopup(_c10);
this.hide();
if(this.bgIframe){
this.bgIframe.hide();
this.bgIframe.size({left:0,top:0,width:0,height:0});
}
if(this.isTopLevel){
dojo.widget.PopupManager.closed(this);
}
this.isShowingNow=false;
try{
this.parent.domNode.focus();
}
catch(e){
}
if(this._bookmark&&dojo.withGlobal(this.openedForWindow||dojo.global(),dojo.html.selection.isCollapsed)){
if(this.openedForWindow){
this.openedForWindow.focus();
}
dojo.withGlobal(this.openedForWindow||dojo.global(),"moveToBookmark",dojo.html.selection,[this._bookmark]);
}
this._bookmark=null;
},closeAll:function(_c11){
if(this.parentPopup){
this.parentPopup.closeAll(_c11);
}else{
this.close(_c11);
}
},setOpenedSubpopup:function(_c12){
this.currentSubpopup=_c12;
},closeSubpopup:function(_c13){
if(this.currentSubpopup==null){
return;
}
this.currentSubpopup.close(_c13);
this.currentSubpopup=null;
},onShow:function(){
dojo.widget.PopupContainer.superclass.onShow.apply(this,arguments);
this.openedSize={w:this.domNode.style.width,h:this.domNode.style.height};
if(dojo.render.html.ie){
if(!this.bgIframe){
this.bgIframe=new dojo.html.BackgroundIframe();
this.bgIframe.setZIndex(this.domNode);
}
this.bgIframe.size(this.domNode);
this.bgIframe.show();
}
this.processQueue();
},processQueue:function(){
if(!this.queueOnAnimationFinish.length){
return;
}
var func=this.queueOnAnimationFinish.shift();
var args=this.queueOnAnimationFinish.shift();
func.apply(this,args);
},onHide:function(){
dojo.widget.HtmlWidget.prototype.onHide.call(this);
if(this.openedSize){
with(this.domNode.style){
width=this.openedSize.w;
height=this.openedSize.h;
}
}
this.processQueue();
}});
dojo.widget.defineWidget("dojo.widget.PopupContainer",[dojo.widget.HtmlWidget,dojo.widget.PopupContainerBase],{});
dojo.widget.PopupManager=new function(){
this.currentMenu=null;
this.currentButton=null;
this.currentFocusMenu=null;
this.focusNode=null;
this.registeredWindows=[];
this.registerWin=function(win){
if(!win.__PopupManagerRegistered){
dojo.event.connect(win.document,"onmousedown",this,"onClick");
dojo.event.connect(win,"onscroll",this,"onClick");
dojo.event.connect(win.document,"onkey",this,"onKey");
win.__PopupManagerRegistered=true;
this.registeredWindows.push(win);
}
};
this.registerAllWindows=function(_c17){
if(!_c17){
_c17=dojo.html.getDocumentWindow(window.top&&window.top.document||window.document);
}
this.registerWin(_c17);
for(var i=0;i<_c17.frames.length;i++){
try{
var win=dojo.html.getDocumentWindow(_c17.frames[i].document);
if(win){
this.registerAllWindows(win);
}
}
catch(e){
}
}
};
this.unRegisterWin=function(win){
if(win.__PopupManagerRegistered){
dojo.event.disconnect(win.document,"onmousedown",this,"onClick");
dojo.event.disconnect(win,"onscroll",this,"onClick");
dojo.event.disconnect(win.document,"onkey",this,"onKey");
win.__PopupManagerRegistered=false;
}
};
this.unRegisterAllWindows=function(){
for(var i=0;i<this.registeredWindows.length;++i){
this.unRegisterWin(this.registeredWindows[i]);
}
this.registeredWindows=[];
};
dojo.addOnLoad(this,"registerAllWindows");
dojo.addOnUnload(this,"unRegisterAllWindows");
this.closed=function(menu){
if(this.currentMenu==menu){
this.currentMenu=null;
this.currentButton=null;
this.currentFocusMenu=null;
}
};
this.opened=function(menu,_c1e){
if(menu==this.currentMenu){
return;
}
if(this.currentMenu){
this.currentMenu.close();
}
this.currentMenu=menu;
this.currentFocusMenu=menu;
this.currentButton=_c1e;
};
this.setFocusedMenu=function(menu){
this.currentFocusMenu=menu;
};
this.onKey=function(e){
if(!e.key){
return;
}
if(!this.currentMenu||!this.currentMenu.isShowingNow){
return;
}
var m=this.currentFocusMenu;
while(m){
if(m.processKey(e)){
e.preventDefault();
e.stopPropagation();
break;
}
m=m.parentPopup;
}
},this.onClick=function(e){
if(!this.currentMenu){
return;
}
var _c23=dojo.html.getScroll().offset;
var m=this.currentMenu;
while(m){
if(dojo.html.overElement(m.domNode,e)||dojo.html.isDescendantOf(e.target,m.domNode)){
return;
}
m=m.currentSubpopup;
}
if(this.currentButton&&dojo.html.overElement(this.currentButton,e)){
return;
}
this.currentMenu.close();
};
};
dojo.provide("dojo.widget.ComboBox");
dojo.widget.incrementalComboBoxDataProvider=function(url,_c26,_c27){
this.searchUrl=url;
this.inFlight=false;
this.activeRequest=null;
this.allowCache=false;
this.cache={};
this.init=function(cbox){
this.searchUrl=cbox.dataUrl;
};
this.addToCache=function(_c29,data){
if(this.allowCache){
this.cache[_c29]=data;
}
};
this.startSearch=function(_c2b,type,_c2d){
if(this.inFlight){
}
var tss=encodeURIComponent(_c2b);
var _c2f=dojo.string.substituteParams(this.searchUrl,{"searchString":tss});
var _c30=this;
var _c31=dojo.io.bind({url:_c2f,method:"get",mimetype:"text/json",load:function(type,data,evt){
_c30.inFlight=false;
if(!dojo.lang.isArray(data)){
var _c35=[];
for(var key in data){
_c35.push([data[key],key]);
}
data=_c35;
}
_c30.addToCache(_c2b,data);
_c30.provideSearchResults(data);
}});
this.inFlight=true;
};
};
dojo.widget.ComboBoxDataProvider=function(_c37,_c38,_c39){
this.data=[];
this.searchTimeout=_c39||500;
this.searchLimit=_c38||30;
this.searchType="STARTSTRING";
this.caseSensitive=false;
this._lastSearch="";
this._lastSearchResults=null;
this.init=function(cbox,node){
if(!dojo.string.isBlank(cbox.dataUrl)){
this.getData(cbox.dataUrl);
}else{
if((node)&&(node.nodeName.toLowerCase()=="select")){
var opts=node.getElementsByTagName("option");
var ol=opts.length;
var data=[];
for(var x=0;x<ol;x++){
var text=opts[x].textContent||opts[x].innerText||opts[x].innerHTML;
var _c41=[String(text),String(opts[x].value)];
data.push(_c41);
if(opts[x].selected){
cbox.setAllValues(_c41[0],_c41[1]);
}
}
this.setData(data);
}
}
};
this.getData=function(url){
dojo.io.bind({url:url,load:dojo.lang.hitch(this,function(type,data,evt){
if(!dojo.lang.isArray(data)){
var _c46=[];
for(var key in data){
_c46.push([data[key],key]);
}
data=_c46;
}
this.setData(data);
}),mimetype:"text/json"});
};
this.startSearch=function(_c48,type,_c4a){
this._preformSearch(_c48,type,_c4a);
};
this._preformSearch=function(_c4b,type,_c4d){
var st=type||this.searchType;
var ret=[];
if(!this.caseSensitive){
_c4b=_c4b.toLowerCase();
}
for(var x=0;x<this.data.length;x++){
if((!_c4d)&&(ret.length>=this.searchLimit)){
break;
}
var _c51=new String((!this.caseSensitive)?this.data[x][0].toLowerCase():this.data[x][0]);
if(_c51.length<_c4b.length){
continue;
}
if(st=="STARTSTRING"){
if(_c4b==_c51.substr(0,_c4b.length)){
ret.push(this.data[x]);
}
}else{
if(st=="SUBSTRING"){
if(_c51.indexOf(_c4b)>=0){
ret.push(this.data[x]);
}
}else{
if(st=="STARTWORD"){
var idx=_c51.indexOf(_c4b);
if(idx==0){
ret.push(this.data[x]);
}
if(idx<=0){
continue;
}
var _c53=false;
while(idx!=-1){
if(" ,/(".indexOf(_c51.charAt(idx-1))!=-1){
_c53=true;
break;
}
idx=_c51.indexOf(_c4b,idx+1);
}
if(!_c53){
continue;
}else{
ret.push(this.data[x]);
}
}
}
}
}
this.provideSearchResults(ret);
};
this.provideSearchResults=function(_c54){
};
this.addData=function(_c55){
this.data=this.data.concat(_c55);
};
this.setData=function(_c56){
this.data=_c56;
};
if(_c37){
this.setData(_c37);
}
};
dojo.widget.defineWidget("dojo.widget.ComboBox",dojo.widget.HtmlWidget,{isContainer:false,forceValidOption:false,searchType:"stringstart",dataProvider:null,startSearch:function(_c57){
},selectNextResult:function(){
},selectPrevResult:function(){
},setSelectedResult:function(){
},autoComplete:true,name:"",textInputNode:null,comboBoxValue:null,comboBoxSelectionValue:null,optionsListWrapper:null,optionsListNode:null,downArrowNode:null,searchTimer:null,searchDelay:100,dataUrl:"",fadeTime:200,disabled:false,maxListLength:8,mode:"local",selectedResult:null,_highlighted_option:null,_prev_key_backspace:false,_prev_key_esc:false,_gotFocus:false,_mouseover_list:false,dataProviderClass:"dojo.widget.ComboBoxDataProvider",buttonSrc:dojo.uri.dojoUri("src/widget/templates/images/combo_box_arrow.png"),dropdownToggle:"fade",templateString:"<span _=\"whitespace and CR's between tags adds &nbsp; in FF\"\n	class=\"dojoComboBoxOuter\"\n	><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n		dojoAttachPoint=\"comboBoxValue\"\n	><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n		dojoAttachPoint=\"comboBoxSelectionValue\"\n	><input type=\"text\" autocomplete=\"off\" class=\"dojoComboBox\"\n		dojoAttachEvent=\"key:_handleKeyEvents; keyUp: onKeyUp; compositionEnd; onResize;\"\n		dojoAttachPoint=\"textInputNode\"\n	><img hspace=\"0\"\n		vspace=\"0\"\n		class=\"dojoComboBox\"\n		dojoAttachPoint=\"downArrowNode\"\n		dojoAttachEvent=\"onMouseUp: handleArrowClick; onResize;\"\n		src=\"${this.buttonSrc}\"\n></span>\n",templateCssString:".dojoComboBoxOuter {\n	border: 0px !important;\n	margin: 0px !important;\n	padding: 0px !important;\n	background: transparent !important;\n	white-space: nowrap !important;\n}\n\n.dojoComboBox {\n	border: 1px inset #afafaf;\n	margin: 0px;\n	padding: 0px;\n	vertical-align: middle !important;\n	float: none !important;\n	position: static !important;\n	display: inline !important;\n}\n\n/* the input box */\ninput.dojoComboBox {\n	border-right-width: 0px !important; \n	margin-right: 0px !important;\n	padding-right: 0px !important;\n}\n\n/* the down arrow */\nimg.dojoComboBox {\n	border-left-width: 0px !important;\n	padding-left: 0px !important;\n	margin-left: 0px !important;\n}\n\n/* IE vertical-alignment calculations can be off by +-1 but these margins are collapsed away */\n.dj_ie img.dojoComboBox {\n	margin-top: 1px; \n	margin-bottom: 1px; \n}\n\n/* the drop down */\n.dojoComboBoxOptions {\n	font-family: Verdana, Helvetica, Garamond, sans-serif;\n	/* font-size: 0.7em; */\n	background-color: white;\n	border: 1px solid #afafaf;\n	position: absolute;\n	z-index: 1000; \n	overflow: auto;\n	cursor: default;\n}\n\n.dojoComboBoxItem {\n	padding-left: 2px;\n	padding-top: 2px;\n	margin: 0px;\n}\n\n.dojoComboBoxItemEven {\n	background-color: #f4f4f4;\n}\n\n.dojoComboBoxItemOdd {\n	background-color: white;\n}\n\n.dojoComboBoxItemHighlight {\n	background-color: #63709A;\n	color: white;\n}\n",templateCssPath:dojo.uri.dojoUri("src/widget/templates/ComboBox.css"),setValue:function(_c58){
this.comboBoxValue.value=_c58;
if(this.textInputNode.value!=_c58){
this.textInputNode.value=_c58;
dojo.widget.html.stabile.setState(this.widgetId,this.getState(),true);
this.onValueChanged(_c58);
}
},onValueChanged:function(){
},getValue:function(){
return this.comboBoxValue.value;
},getState:function(){
return {value:this.getValue()};
},setState:function(_c59){
this.setValue(_c59.value);
},enable:function(){
this.disabled=false;
this.isEnabled=true;
this.textInputNode.removeAttribute("disabled");
},disable:function(){
this.disabled=true;
this.isEnabled=false;
this.textInputNode.setAttribute("disabled",true);
},getCaretPos:function(_c5a){
if(dojo.lang.isNumber(_c5a.selectionStart)){
return _c5a.selectionStart;
}else{
if(dojo.render.html.ie){
var tr=document.selection.createRange().duplicate();
var ntr=_c5a.createTextRange();
tr.move("character",0);
ntr.move("character",0);
try{
ntr.setEndPoint("EndToEnd",tr);
return String(ntr.text).replace(/\r/g,"").length;
}
catch(e){
return 0;
}
}
}
},setCaretPos:function(_c5d,_c5e){
_c5e=parseInt(_c5e);
this.setSelectedRange(_c5d,_c5e,_c5e);
},setSelectedRange:function(_c5f,_c60,end){
if(!end){
end=_c5f.value.length;
}
if(_c5f.setSelectionRange){
_c5f.focus();
_c5f.setSelectionRange(_c60,end);
}else{
if(_c5f.createTextRange){
var _c62=_c5f.createTextRange();
with(_c62){
collapse(true);
moveEnd("character",end);
moveStart("character",_c60);
select();
}
}else{
_c5f.value=_c5f.value;
_c5f.blur();
_c5f.focus();
var dist=parseInt(_c5f.value.length)-end;
var _c64=String.fromCharCode(37);
var tcc=_c64.charCodeAt(0);
for(var x=0;x<dist;x++){
var te=document.createEvent("KeyEvents");
te.initKeyEvent("keypress",true,true,null,false,false,false,false,tcc,tcc);
_c5f.dispatchEvent(te);
}
}
}
},_handleKeyEvents:function(evt){
if(evt.ctrlKey||evt.altKey||!evt.key){
return;
}
this._prev_key_backspace=false;
this._prev_key_esc=false;
var k=dojo.event.browser.keys;
var _c6a=true;
switch(evt.key){
case k.KEY_DOWN_ARROW:
if(!this.popupWidget.isShowingNow){
this.startSearchFromInput();
}
this.highlightNextOption();
dojo.event.browser.stopEvent(evt);
return;
case k.KEY_UP_ARROW:
this.highlightPrevOption();
dojo.event.browser.stopEvent(evt);
return;
case k.KEY_TAB:
if(!this.autoComplete&&this.popupWidget.isShowingNow&&this._highlighted_option){
dojo.event.browser.stopEvent(evt);
this.selectOption({"target":this._highlighted_option,"noHide":false});
this.setSelectedRange(this.textInputNode,this.textInputNode.value.length,null);
}else{
this.selectOption();
return;
}
break;
case k.KEY_ENTER:
if(this.popupWidget.isShowingNow){
dojo.event.browser.stopEvent(evt);
}
if(this.autoComplete){
this.selectOption();
return;
}
case " ":
if(this.popupWidget.isShowingNow&&this._highlighted_option){
dojo.event.browser.stopEvent(evt);
this.selectOption();
this.hideResultList();
return;
}
break;
case k.KEY_ESCAPE:
this.hideResultList();
this._prev_key_esc=true;
return;
case k.KEY_BACKSPACE:
this._prev_key_backspace=true;
if(!this.textInputNode.value.length){
this.setAllValues("","");
this.hideResultList();
_c6a=false;
}
break;
case k.KEY_RIGHT_ARROW:
case k.KEY_LEFT_ARROW:
_c6a=false;
break;
default:
if(evt.charCode==0){
_c6a=false;
}
}
if(this.searchTimer){
clearTimeout(this.searchTimer);
}
if(_c6a){
this.blurOptionNode();
this.searchTimer=setTimeout(dojo.lang.hitch(this,this.startSearchFromInput),this.searchDelay);
}
},compositionEnd:function(evt){
evt.key=evt.keyCode;
this._handleKeyEvents(evt);
},onKeyUp:function(evt){
this.setValue(this.textInputNode.value);
},setSelectedValue:function(_c6d){
this.comboBoxSelectionValue.value=_c6d;
},setAllValues:function(_c6e,_c6f){
this.setSelectedValue(_c6f);
this.setValue(_c6e);
},focusOptionNode:function(node){
if(this._highlighted_option!=node){
this.blurOptionNode();
this._highlighted_option=node;
dojo.html.addClass(this._highlighted_option,"dojoComboBoxItemHighlight");
}
},blurOptionNode:function(){
if(this._highlighted_option){
dojo.html.removeClass(this._highlighted_option,"dojoComboBoxItemHighlight");
this._highlighted_option=null;
}
},highlightNextOption:function(){
if((!this._highlighted_option)||!this._highlighted_option.parentNode){
this.focusOptionNode(this.optionsListNode.firstChild);
}else{
if(this._highlighted_option.nextSibling){
this.focusOptionNode(this._highlighted_option.nextSibling);
}
}
dojo.html.scrollIntoView(this._highlighted_option);
},highlightPrevOption:function(){
if(this._highlighted_option&&this._highlighted_option.previousSibling){
this.focusOptionNode(this._highlighted_option.previousSibling);
}else{
this._highlighted_option=null;
this.hideResultList();
return;
}
dojo.html.scrollIntoView(this._highlighted_option);
},itemMouseOver:function(evt){
if(evt.target===this.optionsListNode){
return;
}
this.focusOptionNode(evt.target);
dojo.html.addClass(this._highlighted_option,"dojoComboBoxItemHighlight");
},itemMouseOut:function(evt){
if(evt.target===this.optionsListNode){
return;
}
this.blurOptionNode();
},onResize:function(){
var _c73=dojo.html.getContentBox(this.textInputNode);
if(_c73.height==0){
dojo.lang.setTimeout(this,"onResize",50);
return;
}
var _c74={width:_c73.height,height:_c73.height};
dojo.html.setContentBox(this.downArrowNode,_c74);
},fillInTemplate:function(args,frag){
dojo.html.applyBrowserClass(this.domNode);
var _c77=this.getFragNodeRef(frag);
if(!this.name&&_c77.name){
this.name=_c77.name;
}
this.comboBoxValue.name=this.name;
this.comboBoxSelectionValue.name=this.name+"_selected";
dojo.html.copyStyle(this.domNode,_c77);
dojo.html.copyStyle(this.textInputNode,_c77);
dojo.html.copyStyle(this.downArrowNode,_c77);
with(this.downArrowNode.style){
width="0px";
height="0px";
}
var _c78;
if(this.mode=="remote"){
_c78=dojo.widget.incrementalComboBoxDataProvider;
}else{
if(typeof this.dataProviderClass=="string"){
_c78=dojo.evalObjPath(this.dataProviderClass);
}else{
_c78=this.dataProviderClass;
}
}
this.dataProvider=new _c78();
this.dataProvider.init(this,this.getFragNodeRef(frag));
this.popupWidget=new dojo.widget.createWidget("PopupContainer",{toggle:this.dropdownToggle,toggleDuration:this.toggleDuration});
dojo.event.connect(this,"destroy",this.popupWidget,"destroy");
this.optionsListNode=this.popupWidget.domNode;
this.domNode.appendChild(this.optionsListNode);
dojo.html.addClass(this.optionsListNode,"dojoComboBoxOptions");
dojo.event.connect(this.optionsListNode,"onclick",this,"selectOption");
dojo.event.connect(this.optionsListNode,"onmouseover",this,"_onMouseOver");
dojo.event.connect(this.optionsListNode,"onmouseout",this,"_onMouseOut");
dojo.event.connect(this.optionsListNode,"onmouseover",this,"itemMouseOver");
dojo.event.connect(this.optionsListNode,"onmouseout",this,"itemMouseOut");
},focus:function(){
this.tryFocus();
},openResultList:function(_c79){
if(!this.isEnabled){
return;
}
this.clearResultList();
if(!_c79.length){
this.hideResultList();
}
if((this.autoComplete)&&(_c79.length)&&(!this._prev_key_backspace)&&(this.textInputNode.value.length>0)){
var cpos=this.getCaretPos(this.textInputNode);
if((cpos+1)>this.textInputNode.value.length){
this.textInputNode.value+=_c79[0][0].substr(cpos);
this.setSelectedRange(this.textInputNode,cpos,this.textInputNode.value.length);
}
}
var even=true;
while(_c79.length){
var tr=_c79.shift();
if(tr){
var td=document.createElement("div");
td.appendChild(document.createTextNode(tr[0]));
td.setAttribute("resultName",tr[0]);
td.setAttribute("resultValue",tr[1]);
td.className="dojoComboBoxItem "+((even)?"dojoComboBoxItemEven":"dojoComboBoxItemOdd");
even=(!even);
this.optionsListNode.appendChild(td);
}
}
this.showResultList();
},onFocusInput:function(){
this._hasFocus=true;
},onBlurInput:function(){
this._hasFocus=false;
this._handleBlurTimer(true,500);
},_handleBlurTimer:function(_c7e,_c7f){
if(this.blurTimer&&(_c7e||_c7f)){
clearTimeout(this.blurTimer);
}
if(_c7f){
this.blurTimer=dojo.lang.setTimeout(this,"checkBlurred",_c7f);
}
},_onMouseOver:function(evt){
if(!this._mouseover_list){
this._handleBlurTimer(true,0);
this._mouseover_list=true;
}
},_onMouseOut:function(evt){
var _c82=evt.relatedTarget;
if(!_c82||_c82.parentNode!=this.optionsListNode){
this._mouseover_list=false;
this._handleBlurTimer(true,100);
this.tryFocus();
}
},_isInputEqualToResult:function(_c83){
var _c84=this.textInputNode.value;
if(!this.dataProvider.caseSensitive){
_c84=_c84.toLowerCase();
_c83=_c83.toLowerCase();
}
return (_c84==_c83);
},_isValidOption:function(){
var tgt=dojo.html.firstElement(this.optionsListNode);
var _c86=false;
while(!_c86&&tgt){
if(this._isInputEqualToResult(tgt.getAttribute("resultName"))){
_c86=true;
}else{
tgt=dojo.html.nextElement(tgt);
}
}
return _c86;
},checkBlurred:function(){
if(!this._hasFocus&&!this._mouseover_list){
this.hideResultList();
if(!this.textInputNode.value.length){
this.setAllValues("","");
return;
}
var _c87=this._isValidOption();
if(this.forceValidOption&&!_c87){
this.setAllValues("","");
return;
}
if(!_c87){
this.setSelectedValue("");
}
}
},sizeBackgroundIframe:function(){
var mb=dojo.html.getMarginBox(this.optionsListNode);
if(mb.width==0||mb.height==0){
dojo.lang.setTimeout(this,"sizeBackgroundIframe",100);
return;
}
},selectOption:function(evt){
var tgt=null;
if(!evt){
evt={target:this._highlighted_option};
}
if(!dojo.html.isDescendantOf(evt.target,this.optionsListNode)){
if(!this.textInputNode.value.length){
return;
}
tgt=dojo.html.firstElement(this.optionsListNode);
if(!tgt||!this._isInputEqualToResult(tgt.getAttribute("resultName"))){
return;
}
}else{
tgt=evt.target;
}
while((tgt.nodeType!=1)||(!tgt.getAttribute("resultName"))){
tgt=tgt.parentNode;
if(tgt===dojo.body()){
return false;
}
}
this.selectedResult=[tgt.getAttribute("resultName"),tgt.getAttribute("resultValue")];
this.setAllValues(tgt.getAttribute("resultName"),tgt.getAttribute("resultValue"));
if(!evt.noHide){
this.hideResultList();
this.setSelectedRange(this.textInputNode,0,null);
}
this.tryFocus();
},clearResultList:function(){
if(this.optionsListNode.innerHTML){
this.optionsListNode.innerHTML="";
}
},hideResultList:function(){
this.popupWidget.close();
},showResultList:function(){
var _c8b=this.optionsListNode.childNodes;
if(_c8b.length){
var _c8c=this.maxListLength;
if(_c8b.length<_c8c){
_c8c=_c8b.length;
}
with(this.optionsListNode.style){
display="";
if(_c8c==_c8b.length){
height="";
}else{
height=_c8c*dojo.html.getMarginBox(_c8b[0]).height+"px";
}
width=(dojo.html.getMarginBox(this.domNode).width-2)+"px";
}
this.popupWidget.open(this.domNode,this,this.downArrowNode);
}else{
this.hideResultList();
}
},handleArrowClick:function(){
this._handleBlurTimer(true,0);
this.tryFocus();
if(this.popupWidget.isShowingNow){
this.hideResultList();
}else{
this.startSearch("");
}
},tryFocus:function(){
try{
this.textInputNode.focus();
}
catch(e){
}
},startSearchFromInput:function(){
this.startSearch(this.textInputNode.value);
},postCreate:function(){
this.onResize();
dojo.event.connect(this,"startSearch",this.dataProvider,"startSearch");
dojo.event.connect(this.dataProvider,"provideSearchResults",this,"openResultList");
dojo.event.connect(this.textInputNode,"onblur",this,"onBlurInput");
dojo.event.connect(this.textInputNode,"onfocus",this,"onFocusInput");
if(this.disabled){
this.disable();
}
var s=dojo.widget.html.stabile.getState(this.widgetId);
if(s){
this.setState(s);
}
}});
dojo.provide("struts.widget.ComboBox");
struts.widget.ComboBoxDataProvider=function(_c8e,_c8f,_c90){
this.data=[];
this.searchTimeout=_c90||500;
this.searchLimit=_c8f||30;
this.searchType="STARTSTRING";
this.caseSensitive=false;
this._lastSearch="";
this._lastSearchResults=null;
this.formId="";
this.formFilter="";
this.firstRequest=true;
this.cbox=null;
this.init=function(cbox,node){
this.cbox=cbox;
this.formId=cbox.formId;
this.formFilter=cbox.formFilter;
this.searchLimit=cbox.searchLimit;
if(!dojo.string.isBlank(cbox.dataUrl)){
this.getData(cbox.dataUrl);
}else{
if((node)&&(node.nodeName.toLowerCase()=="select")){
var opts=node.getElementsByTagName("option");
var ol=opts.length;
var data=[];
for(var x=0;x<ol;x++){
var text=opts[x].textContent||opts[x].innerText||opts[x].innerHTML;
var _c98=[String(text),String(opts[x].value)];
data.push(_c98);
if(opts[x].selected){
cbox.setAllValues(_c98[0],_c98[1]);
}
}
this.setData(data);
}
}
};
this.getData=function(url){
dojo.html.show(this.cbox.indicator);
dojo.io.bind({url:url,formNode:dojo.byId(this.formId),formFilter:window[this.formFilter],load:dojo.lang.hitch(this,function(type,data,evt){
dojo.html.hide(this.cbox.indicator);
if(!this.firstRequest){
this.cbox.notify.apply(this.cbox,[data,type,evt]);
}
var _c9d=null;
var _c9e=data[this.cbox.dataFieldName];
if(!dojo.lang.isArray(data)){
if(_c9e){
if(dojo.lang.isArray(_c9e)){
_c9d=_c9e;
}else{
if(dojo.lang.isObject(_c9e)){
_c9d=[];
for(var key in _c9e){
_c9d.push([_c9e[key],key]);
}
}
}
}else{
var _ca0=[];
for(var key in data){
if(dojo.string.startsWith(key,this.cbox.name)){
_c9d=data[key];
break;
}else{
_ca0.push([data[key],key]);
}
if(!_c9d&&dojo.lang.isArray(data[key])&&!dojo.lang.isString(data[key])){
_c9d=data[key];
}
}
if(!_c9d){
_c9d=_ca0;
}
}
data=_c9d;
}
this.setData(data);
this.firstRequest=false;
}),mimetype:"text/json"});
};
this.startSearch=function(_ca1,type,_ca3){
this._preformSearch(_ca1,type,_ca3);
};
this._preformSearch=function(_ca4,type,_ca6){
var st=type||this.searchType;
var ret=[];
if(!this.caseSensitive){
_ca4=_ca4.toLowerCase();
}
for(var x=0;x<this.data.length;x++){
if(!this.data[x]){
continue;
}
if((!_ca6)&&(ret.length>=this.searchLimit)){
break;
}
var _caa=new String((!this.caseSensitive)?this.data[x][0].toLowerCase():this.data[x][0]);
if(_caa.length<_ca4.length){
continue;
}
if(st=="STARTSTRING"){
if(_ca4==_caa.substr(0,_ca4.length)){
ret.push(this.data[x]);
}
}else{
if(st=="SUBSTRING"){
if(_caa.indexOf(_ca4)>=0){
ret.push(this.data[x]);
}
}else{
if(st=="STARTWORD"){
var idx=_caa.indexOf(_ca4);
if(idx==0){
ret.push(this.data[x]);
}
if(idx<=0){
continue;
}
var _cac=false;
while(idx!=-1){
if(" ,/(".indexOf(_caa.charAt(idx-1))!=-1){
_cac=true;
break;
}
idx=_caa.indexOf(_ca4,idx+1);
}
if(!_cac){
continue;
}else{
ret.push(this.data[x]);
}
}
}
}
}
this.provideSearchResults(ret);
};
this.provideSearchResults=function(_cad){
};
this.addData=function(_cae){
this.data=this.data.concat(_cae);
};
this.setData=function(_caf){
this.data=_caf;
for(var i=0;i<this.data.length;i++){
var _cb1=this.data[i];
if(!dojo.lang.isArray(_cb1)){
this.data[i]=[_cb1,_cb1];
}
}
};
if(_c8e){
this.setData(_c8e);
}
};
dojo.widget.defineWidget("struts.widget.ComboBox",dojo.widget.ComboBox,{widgetType:"ComboBox",dropdownHeight:120,dropdownWidth:0,itemHeight:0,listenTopics:"",notifyTopics:"",notifyTopicsArray:null,indicator:"",formId:"",formFilter:"",dataProviderClass:"struts.widget.ComboBoxDataProvider",loadOnType:false,loadMinimum:3,initialValue:"",initialKey:"",visibleDownArrow:true,fadeTime:100,searchType:"STARTSTRING",dataFieldName:"",keyName:"",templateCssString:"/*\n * $Id: pom.xml 560558 2007-07-28 15:47:10Z apetrelli $\n *\n * Licensed to the Apache Software Foundation (ASF) under one\n * or more contributor license agreements.  See the NOTICE file\n * distributed with this work for additional information\n * regarding copyright ownership.  The ASF licenses this file\n * to you under the Apache License, Version 2.0 (the\n * \"License\"); you may not use this file except in compliance\n * with the License.  You may obtain a copy of the License at\n *\n *  http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing,\n * software distributed under the License is distributed on an\n * \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n * KIND, either express or implied.  See the License for the\n * specific language governing permissions and limitations\n * under the License.\n */\n\n.dojoComboBoxOuter {\n	border: 0px !important;\n	margin: 0px !important;\n	padding: 0px !important;\n	background: transparent !important;\n	white-space: nowrap !important;\n}\n\n.dojoComboBox {\n	border: 1px inset #afafaf;\n	margin: 0px;\n	padding: 0px;\n	vertical-align: middle !important;\n	float: none !important;\n	position: static !important;\n	display: inline;\n}\n\n/* the input box */\ninput.dojoComboBox {\n	border-right-width: 1px !important;\n	margin-right: 0px !important;\n	padding-right: 0px !important;\n}\n\n/* the down arrow */\nimg.dojoComboBox {\n	border-left-width: 0px !important;\n	padding-left: 0px !important;\n	margin-left: 0px !important;\n}\n\n/* IE vertical-alignment calculations can be off by +-1 but these margins are collapsed away */\n.dj_ie img.dojoComboBox {\n	margin-top: 1px;\n	margin-bottom: 1px;\n}\n\n/* the drop down */\n.dojoComboBoxOptions {\n	font-family: Verdana, Helvetica, Garamond, sans-serif;\n	/* font-size: 0.7em; */\n	background-color: white;\n	border: 1px solid #afafaf;\n	position: absolute;\n	z-index: 1000;\n	overflow: auto;\n	cursor: default;\n}\n\n.dojoComboBoxItem {\n	padding-left: 2px;\n	padding-top: 2px;\n	margin: 0px;\n}\n\n.dojoComboBoxItemEven {\n	background-color: #f4f4f4;\n}\n\n.dojoComboBoxItemOdd {\n	background-color: white;\n}\n\n.dojoComboBoxItemHighlight {\n	background-color: #63709A;\n	color: white;\n}\n",templateCssPath:dojo.uri.dojoUri("struts/ComboBox.css"),searchLimit:30,showResultList:function(){
var _cb2=this.optionsListNode.childNodes;
if(_cb2.length){
this.optionsListNode.style.width=this.dropdownWidth===0?(dojo.html.getMarginBox(this.domNode).width-2)+"px":this.dropdownWidth+"px";
if(this.itemHeight===0||dojo.string.isBlank(this.textInputNode.value)){
this.optionsListNode.style.height=this.dropdownHeight+"px";
this.optionsListNode.style.display="";
this.itemHeight=dojo.html.getMarginBox(_cb2[0]).height;
}
var _cb3=this.itemHeight*_cb2.length;
if(_cb3<this.dropdownHeight){
this.optionsListNode.style.height=_cb3+2+"px";
}
this.popupWidget.open(this.domNode,this,this.downArrowNode);
}else{
this.hideResultList();
}
},openResultList:function(_cb4){
if(!this.isEnabled){
return;
}
this.clearResultList();
if(!_cb4.length){
this.hideResultList();
}
if((this.autoComplete)&&(_cb4.length)&&(!this._prev_key_backspace)&&(this.textInputNode.value.length>0)){
var cpos=this.getCaretPos(this.textInputNode);
if((cpos+1)>this.textInputNode.value.length){
this.textInputNode.value+=_cb4[0][0].substr(cpos);
this.setSelectedRange(this.textInputNode,cpos,this.textInputNode.value.length);
}
}
var _cb6=this.textInputNode.value;
var even=true;
while(_cb4.length){
var tr=_cb4.shift();
if(tr){
var td=document.createElement("div");
var text=tr[0];
var i=text.toLowerCase().indexOf(_cb6.toLowerCase());
if(i>=0){
var pre=text.substring(0,i);
var _cbd=text.substring(i,i+_cb6.length);
var post=text.substring(i+_cb6.length);
if(!dojo.string.isBlank(pre)){
td.appendChild(document.createTextNode(pre));
}
var _cbf=document.createElement("b");
td.appendChild(_cbf);
_cbf.appendChild(document.createTextNode(_cbd));
td.appendChild(document.createTextNode(post));
}else{
td.appendChild(document.createTextNode(tr[0]));
}
td.setAttribute("resultName",tr[0]);
td.setAttribute("resultValue",tr[1]);
td.className="dojoComboBoxItem "+((even)?"dojoComboBoxItemEven":"dojoComboBoxItemOdd");
even=(!even);
this.optionsListNode.appendChild(td);
}
}
this.showResultList();
},postCreate:function(){
struts.widget.ComboBox.superclass.postCreate.apply(this);
var self=this;
if(!dojo.string.isBlank(this.listenTopics)){
var _cc1=this.listenTopics.split(",");
for(var i=0;i<_cc1.length;i++){
dojo.event.topic.subscribe(_cc1[i],function(){
var _cc3={cancel:false};
self.notify(this.widgetId,"before",_cc3);
if(_cc3.cancel){
return;
}
self.clearValues();
self.dataProvider.getData(self.dataUrl);
});
}
}
if(!dojo.string.isBlank(this.notifyTopics)){
this.notifyTopicsArray=this.notifyTopics.split(",");
}
this.comboBoxSelectionValue.name=dojo.string.isBlank(this.keyName)?this.name+"Key":this.keyName;
this.comboBoxValue.value=this.initialValue;
this.comboBoxSelectionValue.value=this.initialKey;
this.textInputNode.value=this.initialValue;
if(!this.visibleDownArrow){
dojo.html.hide(this.downArrowNode);
}
if(!dojo.string.isBlank(this.searchType)){
this.dataProvider.searchType=this.searchType.toUpperCase();
}
},clearValues:function(){
this.comboBoxValue.value="";
this.comboBoxSelectionValue.value="";
this.textInputNode.value="";
},onValueChanged:function(data){
this.notify(data,"valuechanged",null);
},notify:function(data,type,e){
if(this.notifyTopicsArray){
dojo.lang.forEach(this.notifyTopicsArray,function(_cc8){
try{
dojo.event.topic.publish(_cc8,data,type,e);
}
catch(ex){
dojo.debug(ex);
}
});
}
},startSearchFromInput:function(){
var _cc9=this.textInputNode.value;
if(this.loadOnType){
if(_cc9.length>=this.loadMinimum){
var _cca=this.dataUrl+(this.dataUrl.indexOf("?")>-1?"&":"?");
_cca+=this.name+"="+_cc9;
this.dataProvider.getData(_cca);
this.startSearch(_cc9);
}else{
this.hideResultList();
}
}else{
this.startSearch(_cc9);
}
}});
dojo.provide("dojo.widget.DropdownContainer");
dojo.widget.defineWidget("dojo.widget.DropdownContainer",dojo.widget.HtmlWidget,{inputWidth:"7em",id:"",inputId:"",inputName:"",iconURL:dojo.uri.dojoUri("src/widget/templates/images/combo_box_arrow.png"),iconAlt:"",inputNode:null,buttonNode:null,containerNode:null,containerToggle:"plain",containerToggleDuration:150,containerAnimInProgress:false,templateString:"<span style=\"white-space:nowrap\"><input type=\"hidden\" name=\"\" value=\"\" dojoAttachPoint=\"valueNode\" /><input name=\"\" type=\"text\" value=\"\" style=\"vertical-align:middle;\" dojoAttachPoint=\"inputNode\" autocomplete=\"off\" /> <img src=\"${this.iconURL}\" alt=\"${this.iconAlt}\" dojoAttachEvent=\"onclick: onIconClick\" dojoAttachPoint=\"buttonNode\" style=\"vertical-align:middle; cursor:pointer; cursor:hand\" /></span>",templateCssPath:"",fillInTemplate:function(args,frag){
var _ccd=this.getFragNodeRef(frag);
this.popup=dojo.widget.createWidget("PopupContainer",{toggle:this.containerToggle,toggleDuration:this.containerToggleDuration});
this.containerNode=this.popup.domNode;
this.domNode.appendChild(this.popup.domNode);
if(this.id){
this.domNode.id=this.id;
}
if(this.inputId){
this.inputNode.id=this.inputId;
}
if(this.inputName){
this.inputNode.name=this.inputName;
}
this.inputNode.style.width=this.inputWidth;
dojo.event.connect(this.inputNode,"onchange",this,"onInputChange");
},onIconClick:function(evt){
if(!this.isEnabled){
return;
}
if(!this.popup.isShowingNow){
this.popup.open(this.inputNode,this,this.buttonNode);
}else{
this.popup.close();
}
},hideContainer:function(){
if(this.popup.isShowingNow){
this.popup.close();
}
},onInputChange:function(){
}});
dojo.provide("dojo.date.serialize");
dojo.date.setIso8601=function(_ccf,_cd0){
var _cd1=(_cd0.indexOf("T")==-1)?_cd0.split(" "):_cd0.split("T");
_ccf=dojo.date.setIso8601Date(_ccf,_cd1[0]);
if(_cd1.length==2){
_ccf=dojo.date.setIso8601Time(_ccf,_cd1[1]);
}
return _ccf;
};
dojo.date.fromIso8601=function(_cd2){
return dojo.date.setIso8601(new Date(0,0),_cd2);
};
dojo.date.setIso8601Date=function(_cd3,_cd4){
var _cd5="^([0-9]{4})((-?([0-9]{2})(-?([0-9]{2}))?)|"+"(-?([0-9]{3}))|(-?W([0-9]{2})(-?([1-7]))?))?$";
var d=_cd4.match(new RegExp(_cd5));
if(!d){
dojo.debug("invalid date string: "+_cd4);
return null;
}
var year=d[1];
var _cd8=d[4];
var date=d[6];
var _cda=d[8];
var week=d[10];
var _cdc=d[12]?d[12]:1;
_cd3.setFullYear(year);
if(_cda){
_cd3.setMonth(0);
_cd3.setDate(Number(_cda));
}else{
if(week){
_cd3.setMonth(0);
_cd3.setDate(1);
var gd=_cd3.getDay();
var day=gd?gd:7;
var _cdf=Number(_cdc)+(7*Number(week));
if(day<=4){
_cd3.setDate(_cdf+1-day);
}else{
_cd3.setDate(_cdf+8-day);
}
}else{
if(_cd8){
_cd3.setDate(1);
_cd3.setMonth(_cd8-1);
}
if(date){
_cd3.setDate(date);
}
}
}
return _cd3;
};
dojo.date.fromIso8601Date=function(_ce0){
return dojo.date.setIso8601Date(new Date(0,0),_ce0);
};
dojo.date.setIso8601Time=function(_ce1,_ce2){
var _ce3="Z|(([-+])([0-9]{2})(:?([0-9]{2}))?)$";
var d=_ce2.match(new RegExp(_ce3));
var _ce5=0;
if(d){
if(d[0]!="Z"){
_ce5=(Number(d[3])*60)+Number(d[5]);
_ce5*=((d[2]=="-")?1:-1);
}
_ce5-=_ce1.getTimezoneOffset();
_ce2=_ce2.substr(0,_ce2.length-d[0].length);
}
var _ce6="^([0-9]{2})(:?([0-9]{2})(:?([0-9]{2})(.([0-9]+))?)?)?$";
d=_ce2.match(new RegExp(_ce6));
if(!d){
dojo.debug("invalid time string: "+_ce2);
return null;
}
var _ce7=d[1];
var mins=Number((d[3])?d[3]:0);
var secs=(d[5])?d[5]:0;
var ms=d[7]?(Number("0."+d[7])*1000):0;
_ce1.setHours(_ce7);
_ce1.setMinutes(mins);
_ce1.setSeconds(secs);
_ce1.setMilliseconds(ms);
if(_ce5!==0){
_ce1.setTime(_ce1.getTime()+_ce5*60000);
}
return _ce1;
};
dojo.date.fromIso8601Time=function(_ceb){
return dojo.date.setIso8601Time(new Date(0,0),_ceb);
};
dojo.date.toRfc3339=function(_cec,_ced){
if(!_cec){
_cec=new Date();
}
var _=dojo.string.pad;
var _cef=[];
if(_ced!="timeOnly"){
var date=[_(_cec.getFullYear(),4),_(_cec.getMonth()+1,2),_(_cec.getDate(),2)].join("-");
_cef.push(date);
}
if(_ced!="dateOnly"){
var time=[_(_cec.getHours(),2),_(_cec.getMinutes(),2),_(_cec.getSeconds(),2)].join(":");
var _cf2=_cec.getTimezoneOffset();
time+=(_cf2>0?"-":"+")+_(Math.floor(Math.abs(_cf2)/60),2)+":"+_(Math.abs(_cf2)%60,2);
_cef.push(time);
}
return _cef.join("T");
};
dojo.date.fromRfc3339=function(_cf3){
if(_cf3.indexOf("Tany")!=-1){
_cf3=_cf3.replace("Tany","");
}
var _cf4=new Date();
return dojo.date.setIso8601(_cf4,_cf3);
};
dojo.provide("dojo.date.common");
dojo.date.setDayOfYear=function(_cf5,_cf6){
_cf5.setMonth(0);
_cf5.setDate(_cf6);
return _cf5;
};
dojo.date.getDayOfYear=function(_cf7){
var _cf8=_cf7.getFullYear();
var _cf9=new Date(_cf8-1,11,31);
return Math.floor((_cf7.getTime()-_cf9.getTime())/86400000);
};
dojo.date.setWeekOfYear=function(_cfa,week,_cfc){
if(arguments.length==1){
_cfc=0;
}
dojo.unimplemented("dojo.date.setWeekOfYear");
};
dojo.date.getWeekOfYear=function(_cfd,_cfe){
if(arguments.length==1){
_cfe=0;
}
var _cff=new Date(_cfd.getFullYear(),0,1);
var day=_cff.getDay();
_cff.setDate(_cff.getDate()-day+_cfe-(day>_cfe?7:0));
return Math.floor((_cfd.getTime()-_cff.getTime())/604800000);
};
dojo.date.setIsoWeekOfYear=function(_d01,week,_d03){
if(arguments.length==1){
_d03=1;
}
dojo.unimplemented("dojo.date.setIsoWeekOfYear");
};
dojo.date.getIsoWeekOfYear=function(_d04,_d05){
if(arguments.length==1){
_d05=1;
}
dojo.unimplemented("dojo.date.getIsoWeekOfYear");
};
dojo.date.shortTimezones=["IDLW","BET","HST","MART","AKST","PST","MST","CST","EST","AST","NFT","BST","FST","AT","GMT","CET","EET","MSK","IRT","GST","AFT","AGTT","IST","NPT","ALMT","MMT","JT","AWST","JST","ACST","AEST","LHST","VUT","NFT","NZT","CHAST","PHOT","LINT"];
dojo.date.timezoneOffsets=[-720,-660,-600,-570,-540,-480,-420,-360,-300,-240,-210,-180,-120,-60,0,60,120,180,210,240,270,300,330,345,360,390,420,480,540,570,600,630,660,690,720,765,780,840];
dojo.date.getDaysInMonth=function(_d06){
var _d07=_d06.getMonth();
var days=[31,28,31,30,31,30,31,31,30,31,30,31];
if(_d07==1&&dojo.date.isLeapYear(_d06)){
return 29;
}else{
return days[_d07];
}
};
dojo.date.isLeapYear=function(_d09){
var year=_d09.getFullYear();
return (year%400==0)?true:(year%100==0)?false:(year%4==0)?true:false;
};
dojo.date.getTimezoneName=function(_d0b){
var str=_d0b.toString();
var tz="";
var _d0e;
var pos=str.indexOf("(");
if(pos>-1){
pos++;
tz=str.substring(pos,str.indexOf(")"));
}else{
var pat=/([A-Z\/]+) \d{4}$/;
if((_d0e=str.match(pat))){
tz=_d0e[1];
}else{
str=_d0b.toLocaleString();
pat=/ ([A-Z\/]+)$/;
if((_d0e=str.match(pat))){
tz=_d0e[1];
}
}
}
return tz=="AM"||tz=="PM"?"":tz;
};
dojo.date.getOrdinal=function(_d11){
var date=_d11.getDate();
if(date%100!=11&&date%10==1){
return "st";
}else{
if(date%100!=12&&date%10==2){
return "nd";
}else{
if(date%100!=13&&date%10==3){
return "rd";
}else{
return "th";
}
}
}
};
dojo.date.compareTypes={DATE:1,TIME:2};
dojo.date.compare=function(_d13,_d14,_d15){
var dA=_d13;
var dB=_d14||new Date();
var now=new Date();
with(dojo.date.compareTypes){
var opt=_d15||(DATE|TIME);
var d1=new Date((opt&DATE)?dA.getFullYear():now.getFullYear(),(opt&DATE)?dA.getMonth():now.getMonth(),(opt&DATE)?dA.getDate():now.getDate(),(opt&TIME)?dA.getHours():0,(opt&TIME)?dA.getMinutes():0,(opt&TIME)?dA.getSeconds():0);
var d2=new Date((opt&DATE)?dB.getFullYear():now.getFullYear(),(opt&DATE)?dB.getMonth():now.getMonth(),(opt&DATE)?dB.getDate():now.getDate(),(opt&TIME)?dB.getHours():0,(opt&TIME)?dB.getMinutes():0,(opt&TIME)?dB.getSeconds():0);
}
if(d1.valueOf()>d2.valueOf()){
return 1;
}
if(d1.valueOf()<d2.valueOf()){
return -1;
}
return 0;
};
dojo.date.dateParts={YEAR:0,MONTH:1,DAY:2,HOUR:3,MINUTE:4,SECOND:5,MILLISECOND:6,QUARTER:7,WEEK:8,WEEKDAY:9};
dojo.date.add=function(dt,_d1d,incr){
if(typeof dt=="number"){
dt=new Date(dt);
}
function fixOvershoot(){
if(sum.getDate()<dt.getDate()){
sum.setDate(0);
}
}
var sum=new Date(dt);
with(dojo.date.dateParts){
switch(_d1d){
case YEAR:
sum.setFullYear(dt.getFullYear()+incr);
fixOvershoot();
break;
case QUARTER:
incr*=3;
case MONTH:
sum.setMonth(dt.getMonth()+incr);
fixOvershoot();
break;
case WEEK:
incr*=7;
case DAY:
sum.setDate(dt.getDate()+incr);
break;
case WEEKDAY:
var dat=dt.getDate();
var _d21=0;
var days=0;
var strt=0;
var trgt=0;
var adj=0;
var mod=incr%5;
if(mod==0){
days=(incr>0)?5:-5;
_d21=(incr>0)?((incr-5)/5):((incr+5)/5);
}else{
days=mod;
_d21=parseInt(incr/5);
}
strt=dt.getDay();
if(strt==6&&incr>0){
adj=1;
}else{
if(strt==0&&incr<0){
adj=-1;
}
}
trgt=(strt+days);
if(trgt==0||trgt==6){
adj=(incr>0)?2:-2;
}
sum.setDate(dat+(7*_d21)+days+adj);
break;
case HOUR:
sum.setHours(sum.getHours()+incr);
break;
case MINUTE:
sum.setMinutes(sum.getMinutes()+incr);
break;
case SECOND:
sum.setSeconds(sum.getSeconds()+incr);
break;
case MILLISECOND:
sum.setMilliseconds(sum.getMilliseconds()+incr);
break;
default:
break;
}
}
return sum;
};
dojo.date.diff=function(dtA,dtB,_d29){
if(typeof dtA=="number"){
dtA=new Date(dtA);
}
if(typeof dtB=="number"){
dtB=new Date(dtB);
}
var _d2a=dtB.getFullYear()-dtA.getFullYear();
var _d2b=(dtB.getMonth()-dtA.getMonth())+(_d2a*12);
var _d2c=dtB.getTime()-dtA.getTime();
var _d2d=_d2c/1000;
var _d2e=_d2d/60;
var _d2f=_d2e/60;
var _d30=_d2f/24;
var _d31=_d30/7;
var _d32=0;
with(dojo.date.dateParts){
switch(_d29){
case YEAR:
_d32=_d2a;
break;
case QUARTER:
var mA=dtA.getMonth();
var mB=dtB.getMonth();
var qA=Math.floor(mA/3)+1;
var qB=Math.floor(mB/3)+1;
qB+=(_d2a*4);
_d32=qB-qA;
break;
case MONTH:
_d32=_d2b;
break;
case WEEK:
_d32=parseInt(_d31);
break;
case DAY:
_d32=_d30;
break;
case WEEKDAY:
var days=Math.round(_d30);
var _d38=parseInt(days/7);
var mod=days%7;
if(mod==0){
days=_d38*5;
}else{
var adj=0;
var aDay=dtA.getDay();
var bDay=dtB.getDay();
_d38=parseInt(days/7);
mod=days%7;
var _d3d=new Date(dtA);
_d3d.setDate(_d3d.getDate()+(_d38*7));
var _d3e=_d3d.getDay();
if(_d30>0){
switch(true){
case aDay==6:
adj=-1;
break;
case aDay==0:
adj=0;
break;
case bDay==6:
adj=-1;
break;
case bDay==0:
adj=-2;
break;
case (_d3e+mod)>5:
adj=-2;
break;
default:
break;
}
}else{
if(_d30<0){
switch(true){
case aDay==6:
adj=0;
break;
case aDay==0:
adj=1;
break;
case bDay==6:
adj=2;
break;
case bDay==0:
adj=1;
break;
case (_d3e+mod)<0:
adj=2;
break;
default:
break;
}
}
}
days+=adj;
days-=(_d38*2);
}
_d32=days;
break;
case HOUR:
_d32=_d2f;
break;
case MINUTE:
_d32=_d2e;
break;
case SECOND:
_d32=_d2d;
break;
case MILLISECOND:
_d32=_d2c;
break;
default:
break;
}
}
return Math.round(_d32);
};
dojo.provide("dojo.date.supplemental");
dojo.date.getFirstDayOfWeek=function(_d3f){
var _d40={mv:5,ae:6,af:6,bh:6,dj:6,dz:6,eg:6,er:6,et:6,iq:6,ir:6,jo:6,ke:6,kw:6,lb:6,ly:6,ma:6,om:6,qa:6,sa:6,sd:6,so:6,tn:6,ye:6,as:0,au:0,az:0,bw:0,ca:0,cn:0,fo:0,ge:0,gl:0,gu:0,hk:0,ie:0,il:0,is:0,jm:0,jp:0,kg:0,kr:0,la:0,mh:0,mo:0,mp:0,mt:0,nz:0,ph:0,pk:0,sg:0,th:0,tt:0,tw:0,um:0,us:0,uz:0,vi:0,za:0,zw:0,et:0,mw:0,ng:0,tj:0,gb:0,sy:4};
_d3f=dojo.hostenv.normalizeLocale(_d3f);
var _d41=_d3f.split("-")[1];
var dow=_d40[_d41];
return (typeof dow=="undefined")?1:dow;
};
dojo.date.getWeekend=function(_d43){
var _d44={eg:5,il:5,sy:5,"in":0,ae:4,bh:4,dz:4,iq:4,jo:4,kw:4,lb:4,ly:4,ma:4,om:4,qa:4,sa:4,sd:4,tn:4,ye:4};
var _d45={ae:5,bh:5,dz:5,iq:5,jo:5,kw:5,lb:5,ly:5,ma:5,om:5,qa:5,sa:5,sd:5,tn:5,ye:5,af:5,ir:5,eg:6,il:6,sy:6};
_d43=dojo.hostenv.normalizeLocale(_d43);
var _d46=_d43.split("-")[1];
var _d47=_d44[_d46];
var end=_d45[_d46];
if(typeof _d47=="undefined"){
_d47=6;
}
if(typeof end=="undefined"){
end=0;
}
return {start:_d47,end:end};
};
dojo.date.isWeekend=function(_d49,_d4a){
var _d4b=dojo.date.getWeekend(_d4a);
var day=(_d49||new Date()).getDay();
if(_d4b.end<_d4b.start){
_d4b.end+=7;
if(day<_d4b.start){
day+=7;
}
}
return day>=_d4b.start&&day<=_d4b.end;
};
dojo.provide("dojo.i18n.common");
dojo.i18n.getLocalization=function(_d4d,_d4e,_d4f){
dojo.hostenv.preloadLocalizations();
_d4f=dojo.hostenv.normalizeLocale(_d4f);
var _d50=_d4f.split("-");
var _d51=[_d4d,"nls",_d4e].join(".");
var _d52=dojo.hostenv.findModule(_d51,true);
var _d53;
for(var i=_d50.length;i>0;i--){
var loc=_d50.slice(0,i).join("_");
if(_d52[loc]){
_d53=_d52[loc];
break;
}
}
if(!_d53){
_d53=_d52.ROOT;
}
if(_d53){
var _d56=function(){
};
_d56.prototype=_d53;
return new _d56();
}
dojo.raise("Bundle not found: "+_d4e+" in "+_d4d+" , locale="+_d4f);
};
dojo.i18n.isLTR=function(_d57){
var lang=dojo.hostenv.normalizeLocale(_d57).split("-")[0];
var RTL={ar:true,fa:true,he:true,ur:true,yi:true};
return !RTL[lang];
};
dojo.provide("dojo.date.format");
(function(){
dojo.date.format=function(_d5a,_d5b){
if(typeof _d5b=="string"){
dojo.deprecated("dojo.date.format","To format dates with POSIX-style strings, please use dojo.date.strftime instead","0.5");
return dojo.date.strftime(_d5a,_d5b);
}
function formatPattern(_d5c,_d5d){
return _d5d.replace(/[a-zA-Z]+/g,function(_d5e){
var s;
var c=_d5e.charAt(0);
var l=_d5e.length;
var pad;
var _d63=["abbr","wide","narrow"];
switch(c){
case "G":
if(l>3){
dojo.unimplemented("Era format not implemented");
}
s=info.eras[_d5c.getFullYear()<0?1:0];
break;
case "y":
s=_d5c.getFullYear();
switch(l){
case 1:
break;
case 2:
s=String(s).substr(-2);
break;
default:
pad=true;
}
break;
case "Q":
case "q":
s=Math.ceil((_d5c.getMonth()+1)/3);
switch(l){
case 1:
case 2:
pad=true;
break;
case 3:
case 4:
dojo.unimplemented("Quarter format not implemented");
}
break;
case "M":
case "L":
var m=_d5c.getMonth();
var _d66;
switch(l){
case 1:
case 2:
s=m+1;
pad=true;
break;
case 3:
case 4:
case 5:
_d66=_d63[l-3];
break;
}
if(_d66){
var type=(c=="L")?"standalone":"format";
var prop=["months",type,_d66].join("-");
s=info[prop][m];
}
break;
case "w":
var _d69=0;
s=dojo.date.getWeekOfYear(_d5c,_d69);
pad=true;
break;
case "d":
s=_d5c.getDate();
pad=true;
break;
case "D":
s=dojo.date.getDayOfYear(_d5c);
pad=true;
break;
case "E":
case "e":
case "c":
var d=_d5c.getDay();
var _d66;
switch(l){
case 1:
case 2:
if(c=="e"){
var _d6b=dojo.date.getFirstDayOfWeek(_d5b.locale);
d=(d-_d6b+7)%7;
}
if(c!="c"){
s=d+1;
pad=true;
break;
}
case 3:
case 4:
case 5:
_d66=_d63[l-3];
break;
}
if(_d66){
var type=(c=="c")?"standalone":"format";
var prop=["days",type,_d66].join("-");
s=info[prop][d];
}
break;
case "a":
var _d6c=(_d5c.getHours()<12)?"am":"pm";
s=info[_d6c];
break;
case "h":
case "H":
case "K":
case "k":
var h=_d5c.getHours();
switch(c){
case "h":
s=(h%12)||12;
break;
case "H":
s=h;
break;
case "K":
s=(h%12);
break;
case "k":
s=h||24;
break;
}
pad=true;
break;
case "m":
s=_d5c.getMinutes();
pad=true;
break;
case "s":
s=_d5c.getSeconds();
pad=true;
break;
case "S":
s=Math.round(_d5c.getMilliseconds()*Math.pow(10,l-3));
break;
case "v":
case "z":
s=dojo.date.getTimezoneName(_d5c);
if(s){
break;
}
l=4;
case "Z":
var _d6e=_d5c.getTimezoneOffset();
var tz=[(_d6e<=0?"+":"-"),dojo.string.pad(Math.floor(Math.abs(_d6e)/60),2),dojo.string.pad(Math.abs(_d6e)%60,2)];
if(l==4){
tz.splice(0,0,"GMT");
tz.splice(3,0,":");
}
s=tz.join("");
break;
case "Y":
case "u":
case "W":
case "F":
case "g":
case "A":
dojo.debug(_d5e+" modifier not yet implemented");
s="?";
break;
default:
dojo.raise("dojo.date.format: invalid pattern char: "+_d5d);
}
if(pad){
s=dojo.string.pad(s,l);
}
return s;
});
}
_d5b=_d5b||{};
var _d70=dojo.hostenv.normalizeLocale(_d5b.locale);
var _d71=_d5b.formatLength||"full";
var info=dojo.date._getGregorianBundle(_d70);
var str=[];
var _d73=dojo.lang.curry(this,formatPattern,_d5a);
if(_d5b.selector!="timeOnly"){
var _d74=_d5b.datePattern||info["dateFormat-"+_d71];
if(_d74){
str.push(_processPattern(_d74,_d73));
}
}
if(_d5b.selector!="dateOnly"){
var _d75=_d5b.timePattern||info["timeFormat-"+_d71];
if(_d75){
str.push(_processPattern(_d75,_d73));
}
}
var _d76=str.join(" ");
return _d76;
};
dojo.date.parse=function(_d77,_d78){
_d78=_d78||{};
var _d79=dojo.hostenv.normalizeLocale(_d78.locale);
var info=dojo.date._getGregorianBundle(_d79);
var _d7b=_d78.formatLength||"full";
if(!_d78.selector){
_d78.selector="dateOnly";
}
var _d7c=_d78.datePattern||info["dateFormat-"+_d7b];
var _d7d=_d78.timePattern||info["timeFormat-"+_d7b];
var _d7e;
if(_d78.selector=="dateOnly"){
_d7e=_d7c;
}else{
if(_d78.selector=="timeOnly"){
_d7e=_d7d;
}else{
if(_d78.selector=="dateTime"){
_d7e=_d7c+" "+_d7d;
}else{
var msg="dojo.date.parse: Unknown selector param passed: '"+_d78.selector+"'.";
msg+=" Defaulting to date pattern.";
dojo.debug(msg);
_d7e=_d7c;
}
}
}
var _d80=[];
var _d81=_processPattern(_d7e,dojo.lang.curry(this,_buildDateTimeRE,_d80,info,_d78));
var _d82=new RegExp("^"+_d81+"$");
var _d83=_d82.exec(_d77);
if(!_d83){
return null;
}
var _d84=["abbr","wide","narrow"];
var _d85=new Date(1972,0);
var _d86={};
for(var i=1;i<_d83.length;i++){
var grp=_d80[i-1];
var l=grp.length;
var v=_d83[i];
switch(grp.charAt(0)){
case "y":
if(l!=2){
_d85.setFullYear(v);
_d86.year=v;
}else{
if(v<100){
v=Number(v);
var year=""+new Date().getFullYear();
var _d8c=year.substring(0,2)*100;
var _d8d=Number(year.substring(2,4));
var _d8e=Math.min(_d8d+20,99);
var num=(v<_d8e)?_d8c+v:_d8c-100+v;
_d85.setFullYear(num);
_d86.year=num;
}else{
if(_d78.strict){
return null;
}
_d85.setFullYear(v);
_d86.year=v;
}
}
break;
case "M":
if(l>2){
if(!_d78.strict){
v=v.replace(/\./g,"");
v=v.toLowerCase();
}
var _d90=info["months-format-"+_d84[l-3]].concat();
for(var j=0;j<_d90.length;j++){
if(!_d78.strict){
_d90[j]=_d90[j].toLowerCase();
}
if(v==_d90[j]){
_d85.setMonth(j);
_d86.month=j;
break;
}
}
if(j==_d90.length){
dojo.debug("dojo.date.parse: Could not parse month name: '"+v+"'.");
return null;
}
}else{
_d85.setMonth(v-1);
_d86.month=v-1;
}
break;
case "E":
case "e":
if(!_d78.strict){
v=v.toLowerCase();
}
var days=info["days-format-"+_d84[l-3]].concat();
for(var j=0;j<days.length;j++){
if(!_d78.strict){
days[j]=days[j].toLowerCase();
}
if(v==days[j]){
break;
}
}
if(j==days.length){
dojo.debug("dojo.date.parse: Could not parse weekday name: '"+v+"'.");
return null;
}
break;
case "d":
_d85.setDate(v);
_d86.date=v;
break;
case "a":
var am=_d78.am||info.am;
var pm=_d78.pm||info.pm;
if(!_d78.strict){
v=v.replace(/\./g,"").toLowerCase();
am=am.replace(/\./g,"").toLowerCase();
pm=pm.replace(/\./g,"").toLowerCase();
}
if(_d78.strict&&v!=am&&v!=pm){
dojo.debug("dojo.date.parse: Could not parse am/pm part.");
return null;
}
var _d95=_d85.getHours();
if(v==pm&&_d95<12){
_d85.setHours(_d95+12);
}else{
if(v==am&&_d95==12){
_d85.setHours(0);
}
}
break;
case "K":
if(v==24){
v=0;
}
case "h":
case "H":
case "k":
if(v>23){
dojo.debug("dojo.date.parse: Illegal hours value");
return null;
}
_d85.setHours(v);
break;
case "m":
_d85.setMinutes(v);
break;
case "s":
_d85.setSeconds(v);
break;
case "S":
_d85.setMilliseconds(v);
break;
default:
dojo.unimplemented("dojo.date.parse: unsupported pattern char="+grp.charAt(0));
}
}
if(_d86.year&&_d85.getFullYear()!=_d86.year){
dojo.debug("Parsed year: '"+_d85.getFullYear()+"' did not match input year: '"+_d86.year+"'.");
return null;
}
if(_d86.month&&_d85.getMonth()!=_d86.month){
dojo.debug("Parsed month: '"+_d85.getMonth()+"' did not match input month: '"+_d86.month+"'.");
return null;
}
if(_d86.date&&_d85.getDate()!=_d86.date){
dojo.debug("Parsed day of month: '"+_d85.getDate()+"' did not match input day of month: '"+_d86.date+"'.");
return null;
}
return _d85;
};
function _processPattern(_d96,_d97,_d98,_d99){
var _d9a=function(x){
return x;
};
_d97=_d97||_d9a;
_d98=_d98||_d9a;
_d99=_d99||_d9a;
var _d9c=_d96.match(/(''|[^'])+/g);
var _d9d=false;
for(var i=0;i<_d9c.length;i++){
if(!_d9c[i]){
_d9c[i]="";
}else{
_d9c[i]=(_d9d?_d98:_d97)(_d9c[i]);
_d9d=!_d9d;
}
}
return _d99(_d9c.join(""));
}
function _buildDateTimeRE(_d9f,info,_da1,_da2){
return _da2.replace(/[a-zA-Z]+/g,function(_da3){
var s;
var c=_da3.charAt(0);
var l=_da3.length;
switch(c){
case "y":
s="\\d"+((l==2)?"{2,4}":"+");
break;
case "M":
s=(l>2)?"\\S+":"\\d{1,2}";
break;
case "d":
s="\\d{1,2}";
break;
case "E":
s="\\S+";
break;
case "h":
case "H":
case "K":
case "k":
s="\\d{1,2}";
break;
case "m":
case "s":
s="[0-5]\\d";
break;
case "S":
s="\\d{1,3}";
break;
case "a":
var am=_da1.am||info.am||"AM";
var pm=_da1.pm||info.pm||"PM";
if(_da1.strict){
s=am+"|"+pm;
}else{
s=am;
s+=(am!=am.toLowerCase())?"|"+am.toLowerCase():"";
s+="|";
s+=(pm!=pm.toLowerCase())?pm+"|"+pm.toLowerCase():pm;
}
break;
default:
dojo.unimplemented("parse of date format, pattern="+_da2);
}
if(_d9f){
_d9f.push(_da3);
}
return "\\s*("+s+")\\s*";
});
}
})();
dojo.date.strftime=function(_da9,_daa,_dab){
var _dac=null;
function _(s,n){
return dojo.string.pad(s,n||2,_dac||"0");
}
var info=dojo.date._getGregorianBundle(_dab);
function $(_db0){
switch(_db0){
case "a":
return dojo.date.getDayShortName(_da9,_dab);
case "A":
return dojo.date.getDayName(_da9,_dab);
case "b":
case "h":
return dojo.date.getMonthShortName(_da9,_dab);
case "B":
return dojo.date.getMonthName(_da9,_dab);
case "c":
return dojo.date.format(_da9,{locale:_dab});
case "C":
return _(Math.floor(_da9.getFullYear()/100));
case "d":
return _(_da9.getDate());
case "D":
return $("m")+"/"+$("d")+"/"+$("y");
case "e":
if(_dac==null){
_dac=" ";
}
return _(_da9.getDate());
case "f":
if(_dac==null){
_dac=" ";
}
return _(_da9.getMonth()+1);
case "g":
break;
case "G":
dojo.unimplemented("unimplemented modifier 'G'");
break;
case "F":
return $("Y")+"-"+$("m")+"-"+$("d");
case "H":
return _(_da9.getHours());
case "I":
return _(_da9.getHours()%12||12);
case "j":
return _(dojo.date.getDayOfYear(_da9),3);
case "k":
if(_dac==null){
_dac=" ";
}
return _(_da9.getHours());
case "l":
if(_dac==null){
_dac=" ";
}
return _(_da9.getHours()%12||12);
case "m":
return _(_da9.getMonth()+1);
case "M":
return _(_da9.getMinutes());
case "n":
return "\n";
case "p":
return info[_da9.getHours()<12?"am":"pm"];
case "r":
return $("I")+":"+$("M")+":"+$("S")+" "+$("p");
case "R":
return $("H")+":"+$("M");
case "S":
return _(_da9.getSeconds());
case "t":
return "\t";
case "T":
return $("H")+":"+$("M")+":"+$("S");
case "u":
return String(_da9.getDay()||7);
case "U":
return _(dojo.date.getWeekOfYear(_da9));
case "V":
return _(dojo.date.getIsoWeekOfYear(_da9));
case "W":
return _(dojo.date.getWeekOfYear(_da9,1));
case "w":
return String(_da9.getDay());
case "x":
return dojo.date.format(_da9,{selector:"dateOnly",locale:_dab});
case "X":
return dojo.date.format(_da9,{selector:"timeOnly",locale:_dab});
case "y":
return _(_da9.getFullYear()%100);
case "Y":
return String(_da9.getFullYear());
case "z":
var _db1=_da9.getTimezoneOffset();
return (_db1>0?"-":"+")+_(Math.floor(Math.abs(_db1)/60))+":"+_(Math.abs(_db1)%60);
case "Z":
return dojo.date.getTimezoneName(_da9);
case "%":
return "%";
}
}
var _db2="";
var i=0;
var _db4=0;
var _db5=null;
while((_db4=_daa.indexOf("%",i))!=-1){
_db2+=_daa.substring(i,_db4++);
switch(_daa.charAt(_db4++)){
case "_":
_dac=" ";
break;
case "-":
_dac="";
break;
case "0":
_dac="0";
break;
case "^":
_db5="upper";
break;
case "*":
_db5="lower";
break;
case "#":
_db5="swap";
break;
default:
_dac=null;
_db4--;
break;
}
var _db6=$(_daa.charAt(_db4++));
switch(_db5){
case "upper":
_db6=_db6.toUpperCase();
break;
case "lower":
_db6=_db6.toLowerCase();
break;
case "swap":
var _db7=_db6.toLowerCase();
var _db8="";
var j=0;
var ch="";
while(j<_db6.length){
ch=_db6.charAt(j);
_db8+=(ch==_db7.charAt(j))?ch.toUpperCase():ch.toLowerCase();
j++;
}
_db6=_db8;
break;
default:
break;
}
_db5=null;
_db2+=_db6;
i=_db4;
}
_db2+=_daa.substring(i);
return _db2;
};
(function(){
var _dbb=[];
dojo.date.addCustomFormats=function(_dbc,_dbd){
_dbb.push({pkg:_dbc,name:_dbd});
};
dojo.date._getGregorianBundle=function(_dbe){
var _dbf={};
dojo.lang.forEach(_dbb,function(desc){
var _dc1=dojo.i18n.getLocalization(desc.pkg,desc.name,_dbe);
_dbf=dojo.lang.mixin(_dbf,_dc1);
},this);
return _dbf;
};
})();
dojo.date.addCustomFormats("dojo.i18n.calendar","gregorian");
dojo.date.addCustomFormats("dojo.i18n.calendar","gregorianExtras");
dojo.date.getNames=function(item,type,use,_dc5){
var _dc6;
var _dc7=dojo.date._getGregorianBundle(_dc5);
var _dc8=[item,use,type];
if(use=="standAlone"){
_dc6=_dc7[_dc8.join("-")];
}
_dc8[1]="format";
return (_dc6||_dc7[_dc8.join("-")]).concat();
};
dojo.date.getDayName=function(_dc9,_dca){
return dojo.date.getNames("days","wide","format",_dca)[_dc9.getDay()];
};
dojo.date.getDayShortName=function(_dcb,_dcc){
return dojo.date.getNames("days","abbr","format",_dcc)[_dcb.getDay()];
};
dojo.date.getMonthName=function(_dcd,_dce){
return dojo.date.getNames("months","wide","format",_dce)[_dcd.getMonth()];
};
dojo.date.getMonthShortName=function(_dcf,_dd0){
return dojo.date.getNames("months","abbr","format",_dd0)[_dcf.getMonth()];
};
dojo.date.toRelativeString=function(_dd1){
var now=new Date();
var diff=(now-_dd1)/1000;
var end=" ago";
var _dd5=false;
if(diff<0){
_dd5=true;
end=" from now";
diff=-diff;
}
if(diff<60){
diff=Math.round(diff);
return diff+" second"+(diff==1?"":"s")+end;
}
if(diff<60*60){
diff=Math.round(diff/60);
return diff+" minute"+(diff==1?"":"s")+end;
}
if(diff<60*60*24){
diff=Math.round(diff/3600);
return diff+" hour"+(diff==1?"":"s")+end;
}
if(diff<60*60*24*7){
diff=Math.round(diff/(3600*24));
if(diff==1){
return _dd5?"Tomorrow":"Yesterday";
}else{
return diff+" days"+end;
}
}
return dojo.date.format(_dd1);
};
dojo.date.toSql=function(_dd6,_dd7){
return dojo.date.strftime(_dd6,"%F"+!_dd7?" %T":"");
};
dojo.date.fromSql=function(_dd8){
var _dd9=_dd8.split(/[\- :]/g);
while(_dd9.length<6){
_dd9.push(0);
}
return new Date(_dd9[0],(parseInt(_dd9[1],10)-1),_dd9[2],_dd9[3],_dd9[4],_dd9[5]);
};
dojo.provide("dojo.widget.TimePicker");
dojo.widget.defineWidget("dojo.widget.TimePicker",dojo.widget.HtmlWidget,function(){
this.time="";
this.useDefaultTime=false;
this.useDefaultMinutes=false;
this.storedTime="";
this.currentTime={};
this.classNames={selectedTime:"selectedItem"};
this.any="any";
this.selectedTime={hour:"",minute:"",amPm:"",anyTime:false};
this.hourIndexMap=["",2,4,6,8,10,1,3,5,7,9,11,0];
this.minuteIndexMap=[0,2,4,6,8,10,1,3,5,7,9,11];
},{isContainer:false,templateString:"<div class=\"timePickerContainer\" dojoAttachPoint=\"timePickerContainerNode\">\n	<table class=\"timeContainer\" cellspacing=\"0\" >\n		<thead>\n			<tr>\n				<td class=\"timeCorner cornerTopLeft\" valign=\"top\">&nbsp;</td>\n				<td class=\"timeLabelContainer hourSelector\">${this.calendar.field-hour}</td>\n				<td class=\"timeLabelContainer minutesHeading\">${this.calendar.field-minute}</td>\n				<td class=\"timeCorner cornerTopRight\" valign=\"top\">&nbsp;</td>\n			</tr>\n		</thead>\n		<tbody>\n			<tr>\n				<td valign=\"top\" colspan=\"2\" class=\"hours\">\n					<table align=\"center\">\n						<tbody dojoAttachPoint=\"hourContainerNode\"  \n							dojoAttachEvent=\"onClick: onSetSelectedHour;\">\n							<tr>\n								<td>12</td>\n								<td>6</td>\n							</tr>\n							<tr>\n								<td>1</td>\n								<td>7</td>\n							</tr>\n							<tr>\n								<td>2</td>\n								<td>8</td>\n							</tr>\n							<tr>\n								<td>3</td>\n								<td>9</td>\n							</tr>\n							<tr>\n								<td>4</td>\n								<td>10</td>\n							</tr>\n							<tr>\n								<td>5</td>\n								<td>11</td>\n							</tr>\n						</tbody>\n					</table>\n				</td>\n				<td valign=\"top\" class=\"minutes\" colspan=\"2\">\n					<table align=\"center\">\n						<tbody dojoAttachPoint=\"minuteContainerNode\" \n							dojoAttachEvent=\"onClick: onSetSelectedMinute;\">\n							<tr>\n								<td>00</td>\n								<td>30</td>\n							</tr>\n							<tr>\n								<td>05</td>\n								<td>35</td>\n							</tr>\n							<tr>\n								<td>10</td>\n								<td>40</td>\n							</tr>\n							<tr>\n								<td>15</td>\n								<td>45</td>\n							</tr>\n							<tr>\n								<td>20</td>\n								<td>50</td>\n							</tr>\n							<tr>\n								<td>25</td>\n								<td>55</td>\n							</tr>\n						</tbody>\n					</table>\n				</td>\n			</tr>\n			<tr>\n				<td class=\"cornerBottomLeft\">&nbsp;</td>\n				<td valign=\"top\" class=\"timeOptions\">\n					<table class=\"amPmContainer\">\n						<tbody dojoAttachPoint=\"amPmContainerNode\" \n							dojoAttachEvent=\"onClick: onSetSelectedAmPm;\">\n							<tr>\n								<td id=\"am\">${this.calendar.am}</td>\n								<td id=\"pm\">${this.calendar.pm}</td>\n							</tr>\n						</tbody>\n					</table>\n				</td>\n				<td class=\"timeOptions\">\n					<div dojoAttachPoint=\"anyTimeContainerNode\" \n						dojoAttachEvent=\"onClick: onSetSelectedAnyTime;\" \n						class=\"anyTimeContainer\">${this.widgetStrings.any}</div>\n				</td>\n				<td class=\"cornerBottomRight\">&nbsp;</td>\n			</tr>\n		</tbody>\n	</table>\n</div>\n",templateCssString:"/*Time Picker */\n.timePickerContainer {\n	width:122px;\n	font-family:Tahoma, Myriad, Helvetica, Arial, Verdana, sans-serif;\n	font-size:16px;\n}\n\n.timeContainer {\n	border-collapse:collapse;\n	border-spacing:0;\n}\n\n.timeContainer thead {\n	color:#293a4b;\n	font-size:0.9em;\n	font-weight:700;\n}\n\n.timeContainer thead td {\n	padding:0.25em;\n	font-size:0.80em;\n	border-bottom:1px solid #6782A8;\n}\n\n.timeCorner {\n	width:10px;\n}\n\n.cornerTopLeft {\n	background: url(\"images/dpCurveTL.png\") top left no-repeat;\n}\n\n.cornerTopRight {\n	background: url(\"images/dpCurveTR.png\") top right no-repeat;\n}\n\n.timeLabelContainer {\n	background: url(\"images/dpMonthBg.png\") top left repeat-x;\n}\n\n.hours, .minutes, .timeBorder {\n	background: #7591bc url(\"images/dpBg.gif\") top left repeat-x;\n\n}\n\n.hours td, .minutes td {\n	padding:0.2em;\n	text-align:center;\n	font-size:0.7em;\n	font-weight:bold;\n	cursor:pointer;\n	cursor:hand;\n	color:#fff;\n}\n\n.minutes {\n	border-left:1px solid #f5d1db;\n}\n\n.hours {\n	border-right:1px solid #6782A8;\n}\n\n.hourSelector {\n	border-right:1px solid #6782A8;\n	padding:5px;\n	padding-right:10px;\n}\n\n.minutesSelector {\n	padding:5px;\n	border-left:1px solid #f5c7d4;\n	text-align:center;\n}\n\n.minutesHeading {\n	padding-left:9px !important;\n}\n\n.timeOptions {\n	background-color:#F9C9D7;\n}\n\n.timeContainer .cornerBottomLeft, .timeContainer .cornerBottomRight, .timeContainer .timeOptions {\n	border-top:1px solid #6782A8;\n}\n\n.timeContainer .cornerBottomLeft {\n	background: url(\"images/dpCurveBL.png\") bottom left no-repeat !important;\n	width:9px !important;\n	padding:0;\n	margin:0;\n}\n\n.timeContainer .cornerBottomRight {\n	background: url(\"images/dpCurveBR.png\") bottom right no-repeat !important;\n	width:9px !important;\n	padding:0;\n	margin:0;\n}\n\n.timeOptions {\n	color:#fff;\n	background:url(\"images/dpYearBg.png\") top left repeat-x;\n\n}\n\n.selectedItem {\n	background-color:#fff;\n	color:#6782a8 !important;\n}\n\n.timeOptions .selectedItem {\n	color:#fff !important;\n	background-color:#9ec3fb !important;\n}\n\n.anyTimeContainer {\n	text-align:center;\n	font-weight:bold;\n	font-size:0.7em;\n	padding:0.1em;\n	cursor:pointer;\n	cursor:hand;\n	color:#fff !important;\n}\n\n.amPmContainer {\n	width:100%;\n}\n\n.amPmContainer td {\n	text-align:center;\n	font-size:0.7em;\n	font-weight:bold;\n	cursor:pointer;\n	cursor:hand;\n	color:#fff;\n}\n\n\n\n/*.timePickerContainer {\n	margin:1.75em 0 0.5em 0;\n	width:10em;\n	float:left;\n}\n\n.timeContainer {\n	border-collapse:collapse;\n	border-spacing:0;\n}\n\n.timeContainer thead td{\n	border-bottom:1px solid #e6e6e6;\n	padding:0 0.4em 0.2em 0.4em;\n}\n\n.timeContainer td {\n	font-size:0.9em;\n	padding:0 0.25em 0 0.25em;\n	text-align:left;\n	cursor:pointer;cursor:hand;\n}\n\n.timeContainer td.minutesHeading {\n	border-left:1px solid #e6e6e6;\n	border-right:1px solid #e6e6e6;	\n}\n\n.timeContainer .minutes {\n	border-left:1px solid #e6e6e6;\n	border-right:1px solid #e6e6e6;\n}\n\n.selectedItem {\n	background-color:#3a3a3a;\n	color:#ffffff;\n}*/\n",templateCssPath:dojo.uri.dojoUri("src/widget/templates/TimePicker.css"),fillInTemplate:function(args,frag){
var _ddc=this.getFragNodeRef(frag);
dojo.html.copyStyle(this.domNode,_ddc);
this.initData();
this.initUI();
},postMixInProperties:function(_ddd,frag){
dojo.widget.TimePicker.superclass.postMixInProperties.apply(this,arguments);
this.calendar=dojo.i18n.getLocalization("dojo.i18n.calendar","gregorian",this.lang);
this.widgetStrings=dojo.i18n.getLocalization("dojo.widget","TimePicker",this.lang);
},initData:function(){
if(this.storedTime.indexOf("T")!=-1&&this.storedTime.split("T")[1]&&this.storedTime!=" "&&this.storedTime.split("T")[1]!="any"){
this.time=dojo.widget.TimePicker.util.fromRfcDateTime(this.storedTime,this.useDefaultMinutes,this.selectedTime.anyTime);
}else{
if(this.useDefaultTime){
this.time=dojo.widget.TimePicker.util.fromRfcDateTime("",this.useDefaultMinutes,this.selectedTime.anyTime);
}else{
this.selectedTime.anyTime=true;
this.time=dojo.widget.TimePicker.util.fromRfcDateTime("",0,1);
}
}
},initUI:function(){
if(!this.selectedTime.anyTime&&this.time){
var _ddf=dojo.widget.TimePicker.util.toAmPmHour(this.time.getHours());
var hour=_ddf[0];
var isAm=_ddf[1];
var _de2=this.time.getMinutes();
var _de3=parseInt(_de2/5);
this.onSetSelectedHour(this.hourIndexMap[hour]);
this.onSetSelectedMinute(this.minuteIndexMap[_de3]);
this.onSetSelectedAmPm(isAm);
}else{
this.onSetSelectedAnyTime();
}
},setTime:function(date){
if(date){
this.selectedTime.anyTime=false;
this.setDateTime(dojo.date.toRfc3339(date));
}else{
this.selectedTime.anyTime=true;
}
this.initData();
this.initUI();
},setDateTime:function(_de5){
this.storedTime=_de5;
},onClearSelectedHour:function(evt){
this.clearSelectedHour();
},onClearSelectedMinute:function(evt){
this.clearSelectedMinute();
},onClearSelectedAmPm:function(evt){
this.clearSelectedAmPm();
},onClearSelectedAnyTime:function(evt){
this.clearSelectedAnyTime();
if(this.selectedTime.anyTime){
this.selectedTime.anyTime=false;
this.time=dojo.widget.TimePicker.util.fromRfcDateTime("",this.useDefaultMinutes);
this.initUI();
}
},clearSelectedHour:function(){
var _dea=this.hourContainerNode.getElementsByTagName("td");
for(var i=0;i<_dea.length;i++){
dojo.html.setClass(_dea.item(i),"");
}
},clearSelectedMinute:function(){
var _dec=this.minuteContainerNode.getElementsByTagName("td");
for(var i=0;i<_dec.length;i++){
dojo.html.setClass(_dec.item(i),"");
}
},clearSelectedAmPm:function(){
var _dee=this.amPmContainerNode.getElementsByTagName("td");
for(var i=0;i<_dee.length;i++){
dojo.html.setClass(_dee.item(i),"");
}
},clearSelectedAnyTime:function(){
dojo.html.setClass(this.anyTimeContainerNode,"anyTimeContainer");
},onSetSelectedHour:function(evt){
this.onClearSelectedAnyTime();
this.onClearSelectedHour();
this.setSelectedHour(evt);
this.onSetTime();
},setSelectedHour:function(evt){
if(evt&&evt.target){
if(evt.target.nodeType==dojo.dom.ELEMENT_NODE){
var _df2=evt.target;
}else{
var _df2=evt.target.parentNode;
}
dojo.event.browser.stopEvent(evt);
dojo.html.setClass(_df2,this.classNames.selectedTime);
this.selectedTime["hour"]=_df2.innerHTML;
}else{
if(!isNaN(evt)){
var _df3=this.hourContainerNode.getElementsByTagName("td");
if(_df3.item(evt)){
dojo.html.setClass(_df3.item(evt),this.classNames.selectedTime);
this.selectedTime["hour"]=_df3.item(evt).innerHTML;
}
}
}
this.selectedTime.anyTime=false;
},onSetSelectedMinute:function(evt){
this.onClearSelectedAnyTime();
this.onClearSelectedMinute();
this.setSelectedMinute(evt);
this.selectedTime.anyTime=false;
this.onSetTime();
},setSelectedMinute:function(evt){
if(evt&&evt.target){
if(evt.target.nodeType==dojo.dom.ELEMENT_NODE){
var _df6=evt.target;
}else{
var _df6=evt.target.parentNode;
}
dojo.event.browser.stopEvent(evt);
dojo.html.setClass(_df6,this.classNames.selectedTime);
this.selectedTime["minute"]=_df6.innerHTML;
}else{
if(!isNaN(evt)){
var _df7=this.minuteContainerNode.getElementsByTagName("td");
if(_df7.item(evt)){
dojo.html.setClass(_df7.item(evt),this.classNames.selectedTime);
this.selectedTime["minute"]=_df7.item(evt).innerHTML;
}
}
}
},onSetSelectedAmPm:function(evt){
this.onClearSelectedAnyTime();
this.onClearSelectedAmPm();
this.setSelectedAmPm(evt);
this.selectedTime.anyTime=false;
this.onSetTime();
},setSelectedAmPm:function(evt){
var _dfa=evt.target;
if(evt&&_dfa){
if(_dfa.nodeType!=dojo.dom.ELEMENT_NODE){
_dfa=_dfa.parentNode;
}
dojo.event.browser.stopEvent(evt);
this.selectedTime.amPm=_dfa.id;
dojo.html.setClass(_dfa,this.classNames.selectedTime);
}else{
evt=evt?0:1;
var _dfb=this.amPmContainerNode.getElementsByTagName("td");
if(_dfb.item(evt)){
this.selectedTime.amPm=_dfb.item(evt).id;
dojo.html.setClass(_dfb.item(evt),this.classNames.selectedTime);
}
}
},onSetSelectedAnyTime:function(evt){
this.onClearSelectedHour();
this.onClearSelectedMinute();
this.onClearSelectedAmPm();
this.setSelectedAnyTime();
this.onSetTime();
},setSelectedAnyTime:function(evt){
this.selectedTime.anyTime=true;
dojo.html.setClass(this.anyTimeContainerNode,this.classNames.selectedTime+" "+"anyTimeContainer");
},onClick:function(evt){
dojo.event.browser.stopEvent(evt);
},onSetTime:function(){
if(this.selectedTime.anyTime){
this.time=new Date();
var _dff=dojo.widget.TimePicker.util.toRfcDateTime(this.time);
this.setDateTime(_dff.split("T")[0]);
}else{
var hour=12;
var _e01=0;
var isAm=false;
if(this.selectedTime["hour"]){
hour=parseInt(this.selectedTime["hour"],10);
}
if(this.selectedTime["minute"]){
_e01=parseInt(this.selectedTime["minute"],10);
}
if(this.selectedTime["amPm"]){
isAm=(this.selectedTime["amPm"].toLowerCase()=="am");
}
this.time=new Date();
this.time.setHours(dojo.widget.TimePicker.util.fromAmPmHour(hour,isAm));
this.time.setMinutes(_e01);
this.setDateTime(dojo.widget.TimePicker.util.toRfcDateTime(this.time));
}
}});
dojo.widget.TimePicker.util=new function(){
this.toRfcDateTime=function(_e03){
if(!_e03){
_e03=new Date();
}
_e03.setSeconds(0);
return dojo.date.strftime(_e03,"%Y-%m-%dT%H:%M:00%z");
};
this.fromRfcDateTime=function(_e04,_e05,_e06){
var _e07=new Date();
if(!_e04||_e04.indexOf("T")==-1){
if(_e05){
_e07.setMinutes(Math.floor(_e07.getMinutes()/5)*5);
}else{
_e07.setMinutes(0);
}
}else{
var _e08=_e04.split("T")[1].split(":");
var _e07=new Date();
_e07.setHours(_e08[0]);
_e07.setMinutes(_e08[1]);
}
return _e07;
};
this.toAmPmHour=function(hour){
var _e0a=hour;
var isAm=true;
if(_e0a==0){
_e0a=12;
}else{
if(_e0a>12){
_e0a=_e0a-12;
isAm=false;
}else{
if(_e0a==12){
isAm=false;
}
}
}
return [_e0a,isAm];
};
this.fromAmPmHour=function(_e0c,isAm){
var hour=parseInt(_e0c,10);
if(isAm&&hour==12){
hour=0;
}else{
if(!isAm&&hour<12){
hour=hour+12;
}
}
return hour;
};
};
dojo.provide("dojo.widget.DropdownTimePicker");
dojo.widget.defineWidget("dojo.widget.DropdownTimePicker",dojo.widget.DropdownContainer,{iconURL:dojo.uri.dojoUri("src/widget/templates/images/timeIcon.gif"),zIndex:"10",displayFormat:"",timeFormat:"",formatLength:"short",value:"",postMixInProperties:function(){
dojo.widget.DropdownTimePicker.superclass.postMixInProperties.apply(this,arguments);
var _e0f=dojo.i18n.getLocalization("dojo.widget","DropdownTimePicker",this.lang);
this.iconAlt=_e0f.selectTime;
},fillInTemplate:function(){
dojo.widget.DropdownTimePicker.superclass.fillInTemplate.apply(this,arguments);
var _e10={widgetContainerId:this.widgetId,lang:this.lang};
this.timePicker=dojo.widget.createWidget("TimePicker",_e10,this.containerNode,"child");
dojo.event.connect(this.timePicker,"onSetTime",this,"onSetTime");
dojo.event.connect(this.inputNode,"onchange",this,"onInputChange");
this.containerNode.style.zIndex=this.zIndex;
this.containerNode.explodeClassName="timeBorder";
if(this.value){
this.timePicker.selectedTime.anyTime=false;
this.timePicker.setDateTime("2005-01-01T"+this.value);
this.timePicker.initData();
this.timePicker.initUI();
this.onSetTime();
}
},onSetTime:function(){
if(this.timePicker.selectedTime.anyTime){
this.inputNode.value="";
}else{
if(this.timeFormat){
dojo.deprecated("dojo.widget.DropdownTimePicker","Must use displayFormat attribute instead of timeFormat.  See dojo.date.format for specification.","0.5");
this.inputNode.value=dojo.date.strftime(this.timePicker.time,this.timeFormat,this.lang);
}else{
this.inputNode.value=dojo.date.format(this.timePicker.time,{formatLength:this.formatLength,datePattern:this.displayFormat,selector:"timeOnly",locale:this.lang});
}
}
this.hideContainer();
},onInputChange:function(){
this.timePicker.time="2005-01-01T"+this.inputNode.value;
this.timePicker.setDateTime(this.timePicker.time);
this.timePicker.initData();
this.timePicker.initUI();
},enable:function(){
this.inputNode.disabled=false;
this.timePicker.enable();
dojo.widget.DropdownTimePicker.superclass.enable.apply(this,arguments);
},disable:function(){
this.inputNode.disabled=true;
this.timePicker.disable();
dojo.widget.DropdownTimePicker.superclass.disable.apply(this,arguments);
}});
dojo.provide("struts.widget.StrutsTimePicker");
dojo.widget.defineWidget("struts.widget.StrutsTimePicker",dojo.widget.DropdownTimePicker,{widgetType:"TimePicker",inputName:"",name:"",postCreate:function(){
struts.widget.StrutsTimePicker.superclass.postCreate.apply(this,arguments);
if(this.value.toLowerCase()=="today"){
this.value=dojo.date.toRfc3339(new Date());
}
this.inputNode.name=this.name;
this.valueNode.name=this.inputName;
},onSetTime:function(){
struts.widget.StrutsTimePicker.superclass.onSetTime.apply(this,arguments);
if(this.timePicker.selectedTime.anyTime){
this.valueNode.value="";
}else{
this.valueNode.value=dojo.date.toRfc3339(this.timePicker.time);
}
}});

