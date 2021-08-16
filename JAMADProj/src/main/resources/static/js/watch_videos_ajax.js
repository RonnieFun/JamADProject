function openForm() {
	  document.getElementById("myForm").style.display = "block";
	}

	function closeForm() {
	  document.getElementById("myForm").style.display = "none";
	}

$(document).ready(function(){
	$("#savePlaylistButton").on("click", function() {
		var userID = document.getElementById("userID").value;
		var playlistID = document.getElementById("playlistID").value;
		var mediaID = document.getElementById("mediaID").value;
		$.ajax({
			type: "POST",
			url: "/addToPlaylist",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				userID :userID,
				playlistID :playlistID,
				mediaID :mediaID
			},
			success: function (response) {
				$("#heartButton").removeClass('userPageHeartIcon').addClass('userPageHeartIconLiked');
				$('#heartButton').attr("onclick", "closeForm()");
			}
		}) 
	});
	
	$('#heartButton').on("click", function() {
		if($("#heartButton").hasClass("userPageHeartIconLiked")) {
			//If already liked, then send Ajax request to unlike it
			var userID = document.getElementById("userID").value;
			var mediaID = document.getElementById("mediaID").value;
			$.ajax({
				type: "POST",
				url: "/removeFromPlaylist",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					userID :userID,
					mediaID :mediaID
				},
				success: function (response) {
					$("#heartButton").removeClass('userPageHeartIconLiked').addClass('userPageHeartIcon');
					$('#heartButton').attr("onclick", "openForm()");
				}
			})
		}
	});
	
	$("#zqButton").on("click", function() {
		if ($("#zqButton").hasClass("zqClass") == false) {
			var artistId = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/subscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistId :artistId
				},
				success: function (response) {
					
					$(".sbutton").text("UNSUBSCRIBE");
					$("#zqButton").addClass('zqClass');
					
				}
				
			}) 
		}
		
	});
	
	$("#zqButton").on("click", function() {
		if ($("#zqButton").hasClass("zqClass")) {
			var artistId = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/unsubscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistId :artistId
				},
				success: function (response) {
					$(".sbutton").text("SUBSCRIBE");
					$("#zqButton").removeClass('zqClass');
					
				}
			}) 
		}
		
	});
});