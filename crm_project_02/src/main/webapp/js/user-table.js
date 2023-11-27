// khi nao file html dc load thi thuc hien gi do

$(document).ready(function() {
	// dang ki su kien click $("ten the || ten class || id").click()
	// class la . id la #
	//this dai dien cho function dang chay
	$(".btn-xoa").click(function() {
		var id = $(this).attr('id-user')
		var This =$(this)

		$.ajax({
			method: "GET",
			url: `http://localhost:8080/crm_project_02/api/user/delete?id=${id}`,
			//data: { name: "John", location: "Boston" } chi danh cho method post
		})
			.done(function(result) {
				//result dai dien cho ket qua tu link url tra ve
				// lay gia tri kieu object trong js thi tenbien.key
				//.parrent di ra the cha
				//.remove xoa the
				//.closet di ra thang cha chi dinh
				if(result.data == true){
					This.closest('tr').remove()
				}
				console.log(result.data)
			});
	})
})