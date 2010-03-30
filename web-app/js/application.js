function permuta(id1, id2) {
	if (document.getElementById(id1).style.visibility == 'hidden') {
		document.getElementById(id1).style.visibility = 'visible';
		document.getElementById(id1).style.display = 'block';
		document.getElementById(id2).style.visibility = 'hidden';
		document.getElementById(id2).style.display = 'none';
	} else {
		document.getElementById(id2).style.visibility = 'visible';
		document.getElementById(id2).style.display = 'block';
		document.getElementById(id1).style.visibility = 'hidden';
		document.getElementById(id1).style.display = 'none';
	}
}
