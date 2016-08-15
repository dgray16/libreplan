function (out) {

    out.push('<div ' + this.domAttrs_(),
            ' z.type="ganttz.resourceload.resourceloadlist.ResourceLoadList">');

    for (var w = this.firstChild; w; w = w.nextSibling) {
        w.redraw(out);
    }

    out.push('</div>');

}

/*
 * Current method is working but timings are bad.
 * If I will use code with delay or after page ready, needed rows are existing,
 * but in timeout/after page load I am not able to push it into page.
 *
 * Also out contains html code that will be on web page.
 * Timeouts, zk.afterMount(), loops - do not help.
 */

/*setTimeout(function () {
 out.push(
 '<div ' + attributes,
 ' z.type="ganttz.resourceload.resourceloadlist.ResourceLoadList">');
 }, 200);*/

/* var node = this;
 var attributes = this.domAttrs_();
 var outVariable = out;

 setTimeout(function () {
 console.log('I am here');

 outVariable.push(
 '<div ' + attributes,
 ' z.type="ganttz.resourceload.resourceloadlist.ResourceLoadList">');

 console.log('I am after');

 for (var w = node.firstChild; w; w = w.nextSibling) {
 console.log(w);
 w.redraw(outVariable);
 }

 outVariable.push('</div>');
 }, 400);*/



/* var node = this.firstChild;

 setTimeout(function() {
 while (node != null) {
 console.log('node', node);
 console.log('node._resourceLoadName', node._resourceLoadName);
 console.log('node.$oid', node.$oid);

 console.log('--');
 if ( node.nextSibling ) {
 console.log('node.nextSibling', node.nextSibling);
 console.log('node.nextSibling._resourceLoadName', node.nextSibling._resourceLoadName);
 console.log('node.nextSibling.$oid', node.nextSibling.$oid);
 }

 node = node.nextSibling;
 }
 }, 300);

 var upperThis = this;
 var outVariable = out;

 zk.afterMount(function() {
 console.log('afterMount()');

 /*out.push('<div id="good-work"></div>'); -- not working */

// jq('.z-resourceloadlist').append('<div id="good-work"></div>'); -- working

/*});*/

/*setTimeout(function() {
    console.log('setTimeout()');*/

    /*for (var w = this.firstChild; w; w = w.nextSibling) {
     w.redraw(outVariable);
     }*/
  /*  console.log(outVariable);*/

    //jq('.z-resourceloadlist').append(outVariable);

/*}, 2000);*/
