/**
 * 
 */
 function openEditForm(){
	document.getElementById("EditForm").style.display="block";
}
 function closeEditForm(){
	document.getElementById("EditForm").style.display="display";
}
window.onclick = function(event) {
  if (event.target == EditForm) {
    EditForm.style.display = "none";
  }
}