
$('#btn-nav-previous').click(function(){
	        $(".menu-inner-box").animate({scrollLeft: "-=150px"});
	    });
	    
	 $('#btn-nav-next').click(function(){
	        $(".menu-inner-box").animate({scrollLeft: "+=150px"});
	    });	

function trigger(obj){
	        let regExpWeak = /[a-z]/;
	        let regExpMedium = /\d+/;
	        let regExpStrong = /.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/;
	        let stringPass=obj.value;
	        
	        if(stringPass.length <= 3 && (stringPass.match(regExpWeak) || stringPass.match(regExpMedium) || stringPass.match(regExpStrong))) 
	        {	$("#text").html("Mật khẩu yếu");
	        	$("#btn-doipass").addClass("disabled");
	        }
	        if(stringPass.length >= 6 && ((stringPass.match(regExpWeak) && stringPass.match(regExpMedium)) || (stringPass.match(regExpMedium) && stringPass.match(regExpStrong)) || (stringPass.match(regExpWeak) && stringPass.match(regExpStrong))))
	         {	$("#text").html("Mật khẩu trung bình");
	         	$("#btn-doipass").removeClass("disabled");   
	         }
	        if(stringPass.length >= 6 && stringPass.match(regExpWeak) && stringPass.match(regExpMedium) && stringPass.match(regExpStrong))
	        	{	$("#text").html("Mật khẩu mạnh");
	        		$("#btn-doipass").removeClass("disabled");
	        	}
};


function isNumberKey(evt) {
    	var charCode = (evt.which) ? evt.which : evt.keyCode;
    	if (charCode > 31 && (charCode < 48 || charCode > 57))
        	return false;
    	return true;
};
	
	
function load_province(){
        $("#province").hide();
        var date = $("#mycal").val();
            $.ajax({
                url : "/xskt/load-province",
                type : "get",
                dataType:"text",
                data : {date                         
                },
                success : function (result){
                    $('#result').html(result);
                }
            });
            
            
};
        
function tracuuvedo() {
		var date=$("#mycal").val();
		var province=$("#provinceAdmin").val();
		var sove=$("#sove").val(); 
                $.ajax({
                    url : "/xskt/tracuuvedo",
                    type : "get",
                    dataType:"text",
                    data : {date, province, sove                        
                    },
                    success : function (result){
                        $('#thongbaoketquado').html(result);
                    }
                });
};
 
  
            
function registerinfo()
	   {
	   var fullName=$("#fullname").val(); 
	   var user=$("#userform").val();
	   var phone=$("#phoneform").val();
	   var mail=$("#emailform").val();
	   $.ajax({
	            url : "/register-user",
	            type : "get",
	            dataType:"text",
	            data : {fullName,user,phone,mail                
	                    },
	            success : function (result){
	            $('#alert-register').html(result);
	                       }
	   }); 
	   } 
	   
function changePass()
{
var oldPass=$("#oldpass").val();  
var newPass=$("#newpass").val(); 
var conPass=$("#conwpass").val(); 
$.ajax({
         url : "/change-pass",
         type : "get",
         dataType:"text",
         data : {oldPass,newPass,conPass                
                 },
         success : function (result){
         $('#alert-change-pass').html(result);
                    }
}); 
}  

function resetPass()
{	
var email = $("#email").val();  
                    $.ajax({
                    url : "/reset-pass",
                    type : "get",
                    dataType:"text",
                    data : {email                 
                    },
                    success : function (result){
                        $('#alert-reset').html(result);
                    }
                });                                                                               
}


function searchhistory(obj)
{	var dateFrom=$("#datefrom").val(); 
    var dateTo=$("#dateto").val();
    var page=obj.value;
                   $.ajax({
                    url : "/xskt/tra-cuu-lich-su-do",
                    type : "get",
                    dataType:"text",
                    data : {dateFrom, dateTo, page                     
                    },
                    success : function (result){
                        $('#resultHistory').html(result);
                    }
                });                                                                                 
}   


function searchProvince(obj){
			$('#resultSearch').show(); 
            var keysearch = $("#province-name").val();
            var page = obj.value; 
                $.ajax({
                    url : "/xskt/admin-search-province",
                    type : "get",
                    dataType:"text",
                    data : {keysearch,page                       
                    },
                    success : function (result){
                        $('#info-province').hide();
                        $('#result-delete').hide();
                        $('#resultSearch').show();
                        $('#resultSearch').html(result);
                    }
                });                
            }     
            
function load_province(obj){ 
            var idProvince  = obj.value;
                $.ajax({
                    url : "/xskt/admin-load-info-province",
                    type : "get",
                    dataType:"text",
                    data : {idProvince                        
                    },
                    success : function (result){
                    	$('#info-province').show();
                        $('#info-province').html(result);
                    }
                });                
            }                 


function areaChanged(obj)
{	    var mien = obj.value;  
        $("#province").hide();   
                    $.ajax({
                    url : "/xskt/admin-load-province",
                    type : "get",
                    dataType:"text",
                    data : {mien                       
                    },
                    success : function (result){
                        $('#resultProvince').html(result);
                    }
                });                                                                                 
}


function searchticket(obj)

{	var idProvince = $("#provinceAdmin").val();
	var dateFrom = $("#dateFrom").val();
	var dateTo = $("#dateTo").val();
	var page = obj.value; 
	$("#resultDetail").hide();	
    $("#province").hide();   
                    $.ajax({
                    url : "/xskt/admin-search-ticket",
                    type : "get",
                    dataType:"text",
                    data : {idProvince,  dateFrom,  dateTo, page                   
                    },
                    success : function (result){
                    	$("#updateForm").hide();
                    	$('#resultSearch').show();
                        $('#resultSearch').html(result);
                    }
                });                                                                                 
};

function detailTicket(id, dateo)
{
var idProvince=id;
let dateKQXS=dateo.value; 
                    $.ajax({
                    url : "/SWP490JPA/admin-detail-KQXS",
                    type : "get",
                    dataType:"text",
                    data : {idProvince,dateKQXS                 
                    },
                    success : function (result){
                    $("#updateForm").hide();
                    $("#resultDetail").show();
                        $('#resultDetail').html(result);
                    }
                });

};

function updateTicketForm(id, dateo)
{
var idProvince=id;
let dateKQXS=dateo.value;
                    $.ajax({
                    url : "/xskt/admin-update-ticket-form",
                    type : "get",
                    dataType:"text",
                    data : {idProvince,dateKQXS                 
                    },
                    success : function (result){
                    $("#updateForm").show();
                     $("#resultDetail").hide();
                        $('#updateForm').html(result);
                    }
                });

};


function searchUser(obj)
{
var username=$("#username").val();
var phone=$("#phone").val();
var page=obj.value;
$.ajax({
         url : "/xskt/admin-search-user",
         type : "get",
         dataType:"text",
         data : {username,phone, page                 
                 },
         success : function (result){
         $('#ressult-delete').hide(); 
         $('#form-update-user').hide(); 
         $("#resultSearchUser").show();
         $('#resultSearchUser').html(result);
                    }
}); 
}  



    
    
