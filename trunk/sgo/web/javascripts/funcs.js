
//Funções javascript compartilhadas pelo sistema.

//retorna a data Atual no formato dd/mm/yyyy
function getCurrentDate() {

	var fullDate = new Date();
	var twoDigitMonth = ((fullDate.getMonth().length+1) != 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var currentDate = fullDate.getDate() + "/" + twoDigitMonth + "/" + fullDate.getFullYear();

	return currentDate;

}