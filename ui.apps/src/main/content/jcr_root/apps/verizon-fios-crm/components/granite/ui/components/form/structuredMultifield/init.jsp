<%--
  ADOBE CONFIDENTIAL

  Copyright 2013 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page session="false"
          import="java.lang.reflect.Array,
                  java.util.HashMap,
                  org.apache.sling.api.wrappers.ValueMapDecorator,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Field" %><%

    Config cfg = cmp.getConfig();

    Resource field = cfg.getChild("field");
    Config fieldCfg = new Config(field);
    String name = fieldCfg.get("name", String.class);
    Object value = cmp.getValue().getContentValue(name, null); // don't convert; pass null for type
    Object[] array = new Object[0];
    if (value != null) {
        if (value.getClass().isArray()) {
            array = (Object[]) value;
        } else {
            array = (Object[]) Array.newInstance(value.getClass(), 1);
            array[0] = value;
        }
    }

    ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
    vm.put("value", array);

    request.setAttribute(Field.class.getName(), vm);
%>
