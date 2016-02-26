$(function(){
//Format Zero Value
	var number = numeral(0);

	numeral.zeroFormat('-');
	
	$('#dailyFlashTable').calx();
	
	
	$('#scheduleReportTable').find('td:nth-child(10),th:nth-child(10)').hide();
	$('#scheduleReportTable').find('td:nth-child(11),th:nth-child(11)').hide();
	$('#scheduleReportTable').find('td:nth-child(12),th:nth-child(12)').hide();
	$('#scheduleReportTable').find('td:nth-child(13),th:nth-child(13)').hide();
	$('#scheduleReportTable').find('td:nth-child(14),th:nth-child(14)').hide();
	$('#scheduleReportTable').find('td:nth-child(15),th:nth-child(15)').hide();	
	$('#scheduleReportTable').find('td:nth-child(16),th:nth-child(16)').hide();
	$('#scheduleReportTable').find('td:nth-child(17),th:nth-child(17)').hide();
	
    $('#saveFlashReport').on({
        click: function(){
        	var dataObj = '{"preOpenHour":"' + $("#A0").val() + '",';
        	dataObj += '"closeHour":"'+ $("#closeHour").find("input").val() + '",';
        	dataObj += '"delivered":"' + $("#deliveredActualSales").val() + '",';
        	dataObj += '"planned":"' + $("#plannedActualSales").val() + '",';
        	dataObj += '"storeId":"' + $("#storeId").val() + '",';
            dataObj +='"details" : [ ';
        	$("tr[id^='row']").each(function(i,row){
        		dataObj += '{"strHour": "' + $(row).find("td:eq(1)").text() + '",';
        		dataObj += ' "strIdealHour": "' + $(row).find("input[id^='K']").val() + '",';
        		dataObj += '"strActualSale": "' + $(row).find("input[id^='B']").val() + '",';
        		dataObj += ' "strActualHour": "' + $(row).find("input[id^='A']").val() + '"},';
        		
        	});
        	
        	
        	dataObj=dataObj.substring(0, dataObj.length - 1);
        	dataObj += ']}';
        	
        	var sw = screen.width;
        	
		
            $.ajax({
                type : 'POST',
                dataType: 'json',
                contentType: 'application/json',
                url: url,
                data: dataObj,
        		beforeSend: function() {
        			
        			var styles = {
        					"visibility": "visible",
        					"left": Math.round((sw/2) - 100)
        			};
        			$('#splash').css(styles);
        			$(window).scrollTop(0);
        			
        		},
                success: function(data) {
                	$('#splash').css("visibility","hidden");
                	$('#result').find('#message').text(data);
                	$('#result').css("left", Math.round((sw/2) - 100));
                	$('#result').fadeIn("slow").delay(5000).fadeOut("slow");
                },
                error: function (xhr, ajaxOptions, thrownError) {
                	$('#splash').css("visibility","hidden");
                	$('#result').find('#message').text(xhr.status + " - " + thrownError);
                	$('#result').css("left", Math.round((sw/2) - 100));
                	$('#result').fadeIn("slow").delay(10000).fadeOut("slow");
                  }
            });
            return false;
        },
        mouseover: function() {
            $(this).addClass("ui-state-hover");
            $(this).css("cursor","pointer");
        	
        },
        mouseout: function() {
            $(this).removeClass("ui-state-hover");

        }
    	
    });
    
    $("input[id^='A'],input[id^='B']").on({
    	focus: function(){
    		$(this).val('');
    	}
    });
    
    $("tr[id^='row']").find("input[id^='A']").on({
    	change: function(){
    		var row = $(this).parent().parent();
    		var value=$(row).find("td:eq(15)").text();
    		$(row).find("td:eq(16)").find("input").val(value).trigger('change');
    	}
    });
    
    $("tr[id^='row']").find("input[id^='B']").on({
    	change: function(){
	    		
	    		if($(this).val() > 0){
		    		
		    		var dataObj = '{"storeId":"' + $("#storeId").val() + '",';
		    		dataObj += '"sales": [';
		    		$("tr[id^='row']").each(function(i,row){
		        		dataObj += '{"strTime": "' + $(row).find("td:eq(1)").text() + '",';
		        		dataObj += '"strSale": "' + $(row).find("td:eq(13)").text() + '"},';
		    		});
		    		
		    		dataObj=dataObj.substring(0, dataObj.length - 1);
		    		dataObj += "]}";
		    		
		            $.ajax({
		                type : 'POST',
		                dataType: 'json',
		                contentType: 'application/json',
		                url: calulateUrl,
		                data: dataObj,
		                success: function(data) {
		                	refreshIdealHours(data);
		                },
		            });
	    		
    		}
    	},
    });
    
    function refreshIdealHours(data){
    	
    	var hours = data.split(",");
    	var totalIdealHours=0;
    	var totalCumulIdealHours=0;
    	$("tr[id^='row']").each(function(i,row){
    		$(row).find("td:eq(15)").find("input").val(hours[i]).trigger('change');
    	});
    }
       
});