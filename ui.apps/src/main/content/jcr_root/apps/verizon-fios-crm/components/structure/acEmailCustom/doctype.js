use(function () {

    if (currentDesign != null) {
        currentDesign.getDoctype(currentStyle).toRequest(request);
    }

    return {
        declaration: Packages.com.day.cq.commons.Doctype.fromRequest(request).getDeclaration()
    };
});