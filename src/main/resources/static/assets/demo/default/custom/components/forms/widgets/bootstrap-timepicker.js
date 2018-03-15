//== Class definition

var BootstrapTimepicker = function () {
    
    //== Private functions
    var demos = function () {
        // minimum setup
        $('#m_timepicker_2_modal, #m_timepicker_1_modal').timepicker({
            showMeridian:false
        });




    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();

jQuery(document).ready(function() {    
    BootstrapTimepicker.init();
});