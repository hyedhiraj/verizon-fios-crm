(function ($, $document) {
    "use strict";


    var COMPOSITE_REQUIRED = 'data-composite=true';
    var COMPOSITE_NO_REQUIRED = 'data-composite=false';
    var MULTIFIELD_NAME = 'data-name';


    var rootNodeExist = new Boolean(false),
        rootNodePath;

    //Initialize Multifield
    $(document).ready(function() {

        //GET
        addDataInFields();

        //POST
        collectDataFromFields();
    });

    //1. GET Data from CRX and build multifield
    function addDataInFields() {

        $(document).on("dialog-ready", function() {



            var $form = $(".coral-structuredMultifield").parents(".cq-dialog");
            var $formActionURL = $form.attr("action");
            var $multiFieldNode = $form.find("[" + COMPOSITE_REQUIRED + "]");
            var $noMultifieldNode = $form.find("[" + COMPOSITE_NO_REQUIRED + "]");
            rootNodePath = $formActionURL;

            $multiFieldNode.each(function(){

                var node = $(this);
                var $parentName = node.attr(MULTIFIELD_NAME);
                var mNames = getMultiFieldNames(node);

                if(~$parentName.indexOf("./")){$parentName = $parentName.substring(1);}
				
                var actionUrl = rootNodePath +  $parentName + ".infinity.json";

                if(typeof $parentName !== null || $parentName === null){
                    $.ajax({
                        type: "GET",
                        url: actionUrl,
                        async: false,
                        statusCode:{
                            404: function(){
                                console.log("Multi-Field Node root doesn't exist: " + $parentName);
                                return;
                            }
                        }
                    }).done(postProcess);
                }
                function postProcess(data) {
                    rootNodeExist = true;
                    _.each(mNames, function($multiFieldNode, mName) {
                        _.each(data, function(key, value) {
                            if (value.includes(mName)) {
                                //console.log(key, $multiFieldNode, mName);
                                buildMultiField(key, $multiFieldNode, mName);
                                console.log('call to buildMultiField is commented ')
                            }
                        });
                    });
                }
                //Return Array Names
                function getMultiFieldNames($multifields) {
                    var mNames = {},
                        mName;

                    $multifields.each(function(i, multifield) {
                        mName = $(multifield).children("[name$='@Delete']").attr("name");
                        mName = mName.substring(0, mName.indexOf("@"));
                        mName = mName.substring(2);
                        mNames[mName] = $(multifield);
                    });
                    return mNames;
                }

                //GET items and fetch data to the fields
                function buildMultiField(data, $multifield, mName) {

                    if (_.isEmpty(mName) || _.isEmpty(data)) { return; }

                    $multifield.find(".js-coral-Multifield-add").click();

                    _.each(data, function(fValue, fKey) {

                        if (fKey == "jcr:primaryType") {return;}

                        var $field = $multifield.find("[name='./" + fKey + "']").last();

                        if (_.isEmpty($field)) { 
                        		return;  
                        } 
                        
                        var select = $field.closest(".coral3-Select");
                        if($field.closest(".coral3-Select").length > 0){
                            select.val(fValue);
                        } else {
                        		$field.val(fValue);
                        }

                    });
                }
            });

        });
    }

    //2. POST Data to CRX by collecting data from all the fields
    function collectDataFromFields() {

        //2a.SUBMTI
        $(document).on("click", ".cq-dialog-submit", function(event) {

            var $form = $(".coral-structuredMultifield").parents(".cq-dialog");
            var $multiFieldNode = $form.find("[" + COMPOSITE_REQUIRED + "]");

            $multiFieldNode.each(function(counter, fieldSet){

                var $node = $(this);

                var $parentName = $node.attr(MULTIFIELD_NAME);

                if(~$parentName.indexOf("./")){$parentName = $parentName.substring(1);}


                //DELETE old nodes to POST new ones
                deleteRootNode($parentName);


                var $structuredField = $node.find("[class='coral-Form-fieldset']");


                 //NEW LIST OF ITEMS (ROOT NODE)
                $structuredField.each(function(j, field){
                    var $structuredFields = $(field).find("[class='coral-Form-fieldwrapper']");
                    $structuredFields.each(function(i,fields){
						fillValueHelper($form, $parentName +"/"+ $structuredField.data("name"), $(fields).find("[name]"), j);
                    });
                    //fillValueHelper($form, $parentName + $structuredField.data("name"), $(field).find("[name]"), j);
                });
            });
            deleteExtraFieldsTag($form);

        });

        function fillValueHelper($form, fieldSetName, $field, $counter) {

            var name = $field.attr("name");
            var $form = $(".cq-dialog");
            var $multiFieldNode = $form.find("[" + COMPOSITE_REQUIRED + "]");
            if (!name) { return; }
            $('<input />')
                .attr('type', 'hidden')
                .attr('name', "." + fieldSetName + "-" + ++$counter + "/" + name)
                .attr('value', $field.val())
                .appendTo($form);

        }
    }

    function deleteRootNode($path) {
        var fullPath = rootNodePath + $path;
        var rootNodeUrl = fullPath + ".infinity.json";

        $.ajax({
            type: "GET",
            url: rootNodeUrl,
            async: false,
            statusCode: {
                404: function() {
                    console.log( "Node doesn't exist ");
                    return;
                }
            }
        }).done(evaluate);

        function evaluate(data) {
            //If data has more than 'jcr:primaryType: "nt:unstructured"'
            if(Object.keys(data).length > 1){
                deleteHelper(fullPath, 1);
            }
        }
    }
    function deleteExtraFieldsTag($form){
        var $ols = $form.find("ol[class='coral-Multifield-list js-coral-Multifield-list']");
        $ols.remove();
    }

    function deleteHelper($actionUrl, op) {

        if(op>=10){
            return;
        }
        $.ajax({
            type: "POST",
            url: $actionUrl,
            async: false,
            data: {
                ":operation": "delete"
            },
            statusCode: {
                500: function() {
                    console.log( "Node wasn't delete ");
                    deleteHelper($actionUrl, ++op);
                }
            }
        }).done(function() { console.log("Call was done.");});
    }


})($, $(document));