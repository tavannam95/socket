var sys = {
	mariaDB : {
		ajax: function(url, param, type="get"){
			var result;
			
			$.ajaxSettings.traditional = true;
			$.ajax({
				url : url,
				type : type,
				async : false,	//동기화
				data : param,
				dataType: 'json',
				contentType: 'application/json',
				error : function (req, status, err){
				},
				success : function(data){
					result = data;
				}
			});
			return result;
		},
	},

	convertParam: function(param) {
		for(let [key, value] of Object.entries(param)) {
			param[key] = ['object', 'array'].includes(sys.getType(value)) ? JSON.stringify(value) : value;
		}
		return param;
	},
	
	getData: function(path, param){
		return sys.mariaDB.ajax(path, param);
	},
	
	getType: function(t) {
        return Object.prototype.toString.call(t).slice(8, -1).toLowerCase();
    },

}