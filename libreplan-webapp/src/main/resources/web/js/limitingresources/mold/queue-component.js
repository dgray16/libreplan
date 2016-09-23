function(out) {

    /* After we migrated from ZK5 to ZK8, this.domAttrs_() started to return NaN.
     * Possible reason: not enough time to load library. 
     */
    if ( !isNaN(this.domAttrs_()) ) {
        out.push(
            '<div ',
            this.domAttrs_(),
            'class="row_resourceload"',
            'z.autoz="true"',
            '>');
    } else {
        out.push(
            '<div ',
            'class="row_resourceload"',
            'z.autoz="true"',
            '>');
    }
    
    out.push('<span class="resourceload_name">');
    out.push(this.getResourceName());
    out.push('</span>');

    for (var w = this.firstChild; w; w = w.nextSibling)
        w.redraw(out);

    out.push('</div>');
}