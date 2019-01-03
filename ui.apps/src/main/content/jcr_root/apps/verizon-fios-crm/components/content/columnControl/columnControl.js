"use strict";
use(function () {
    var column = granite.resource.properties["columns"];
    var columns = column.split("-");

    return columns;
});