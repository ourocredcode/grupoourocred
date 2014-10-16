jQuery.fn.dataTableExt.oSort['custom_euro_date_hour-asc'] = function(x, y) {

    var xVal = getCustomEuroDateHourValue(x);
    var yVal = getCustomEuroDateHourValue(y);

    if (xVal < yVal) {
	return -1;
    } else if (xVal > yVal) {
	return 1;
    } else {
	return 0;
    }

};
	 
jQuery.fn.dataTableExt.oSort['custom_euro_date_hour-desc'] = function(x, y) {

    var xVal = getCustomEuroDateHourValue(x);
    var yVal = getCustomEuroDateHourValue(y);

    if (xVal < yVal) {
	return 1;
    } else if (xVal > yVal) {
	return -1;
    } else {
	return 0;
    }

};

jQuery.fn.dataTableExt.oSort['custom_euro_date-asc'] = function(x, y) {

    var xVal = getCustomEuroDateValue(x);
    var yVal = getCustomEuroDateValue(y);

    if (xVal < yVal) {
	return -1;
    } else if (xVal > yVal) {
	return 1;
    } else {
	return 0;
    }

};
	 
jQuery.fn.dataTableExt.oSort['custom_euro_date-desc'] = function(x, y) {

    var xVal = getCustomEuroDateValue(x);
    var yVal = getCustomEuroDateValue(y);

    if (xVal < yVal) {
	return 1;
    } else if (xVal > yVal) {
	return -1;
    } else {
	return 0;
    }

};

function getCustomEuroDateHourValue(strDate) {

    var frDatea = $.trim(strDate).split(' ');
    var frTimea = frDatea[1].split(':');
    var frDatea2 = frDatea[0].split('/');

    var x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] );

    x = x * 1;

    return x;

}

function getCustomEuroDateValue(strDate) {

    var frDatea = $.trim(strDate).split(' ');
    var frDatea2 = frDatea[0].split('/');

    var x = (frDatea2[2] + frDatea2[1] + frDatea2[0]);

    x = x * 1;

    return x;

}