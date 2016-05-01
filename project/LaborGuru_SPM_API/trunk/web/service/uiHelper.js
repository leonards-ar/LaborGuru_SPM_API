angular.module('web').factory('uiHelper',function() {

	function setTitle(title){
		$rootScope.sectionTitle = title;
	}

	function getURI(relativeURL) {
	    return configuration.host + relativeURL;
	}
	function generateUUID(){
			var d = new Date().getTime();
			var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
					var r = (d + Math.random()*16)%16 | 0;
					d = Math.floor(d/16);
					return (c=== 'x' ? r : (r&0x3|0x8)).toString(16);
			});
			return uuid;
	}

	function getUIListFrom(list, emptyName){

		if (list !== undefined) {
			try{
				var copy = JSON.parse(JSON.stringify(list));
				copy.splice(0, 0, {id : null, name : emptyName});
				return copy;
			}

			catch(e){
				console.error(e);
				console.error(list);
			}
		}

		return [];
	}

	function getUIItemDescriptionFrom(list, value){
			var enumItem = list;

			for(var key in enumItem){
				var item = enumItem[key];
				if(item.id === value){
						return item.name;
				}
			}
			return "invalid";
	}

	function getUIList(enumName, emptyName){
			return getUIListFrom($localStorage.enums[enumName], emptyName);
	}

	function getUIItemDescription(enumName, value){
			return getUIItemDescriptionFrom($localStorage.enums[enumName], value);
	}

	function getUIListLoading(){
			return getUIListFrom([], "Loading...");
	}

	function getRawDTO(viewItem){
		var copy = angular.copy(viewItem);
		delete copy["errors"];
		angular.forEach(copy, function(value, key){
			if(key.indexOf("__") === 0){
				delete copy[key];
			}
		});

		return copy;
	}

	// function showProcessing(){
	//
	// }
	//
	// function hideProcessing(){
	//
	// }

	var uiHelper = {};
	uiHelper.setTitle = setTitle;
	uiHelper.getURI = getURI;
	uiHelper.generateUUID = generateUUID;
	uiHelper.getUIListFrom = getUIListFrom;
	uiHelper.getUIItemDescriptionFrom = getUIItemDescriptionFrom;
	uiHelper.getUIList = getUIList;
	uiHelper.getUIItemDescription = getUIItemDescription;
	uiHelper.getUIListLoading = getUIListLoading;
	uiHelper.getEnumList = getEnumList;
	// uiHelper.showProcessing = showProcessing;
	// uiHelper.hideProcessing = hideProcessing;

	uiHelper.getRawDTO = getRawDTO;



	return uiHelper;

});
