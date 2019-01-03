/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2014 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
"use strict";

use(["/libs/wcm/foundation/components/utils/ResourceUtils.js"], function (ResourceUtils) {
    
     return {
         leftCol: granite.resource.resolve(granite.resource.path + "/parLeft").catch(function(){ return null;}),
         rightCol: granite.resource.resolve(granite.resource.path + "/parRight").catch(function(){ return null;}),
         redirect: granite.resource.resolve(granite.resource.path + "/redirect").catch(function(){ return null;}),
    };
});
