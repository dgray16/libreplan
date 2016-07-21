zk.$package("ganttz.resourceload");

ganttz.resourceload.ResourceLoadList = zk.$extends(
    zk.Widget,
    {
        $init : function() {
            this.$supers('$init', arguments);
            this.$class.setInstance(this);
            common.Common.mixInDayPositionRestorer(this);
        },
        bind_ : function(evt) {
            this.$supers('bind_', arguments);
            this.domListen_(jq(window), 'onResize', 'adjustTimeTrackerSize');
            this.domListen_(jq('.rightpanel-layout div:first'), 'onScroll', '_listenToScroll');
        },
        unbind_ : function(evt) {
            this.domUnlisten_(jq(window), 'onResize', 'adjustTimeTrackerSize');
            this.domUnlisten_(jq('.rightpanel-layout div:first'), 'onScroll', '_listenToScroll');
            this.$supers('unbind_', arguments);
        },
        _divsToRestoreDayInto: function() {
            var first = this.$n();
            return [first, first.parentNode, first.parentNode.parentNode];
        },
        recalculateTimeTrackerHeight : function() {
            var DOMResourceLoadList = jq('.resourceloadlist');
            var DOMfirstWatermarkColumn = jq('.rightpanel-layout tr#watermark td :first');

            if ( DOMResourceLoadList != undefined && DOMfirstWatermarkColumn != undefined){
                DOMResourceLoadList.height(
                    Math.max(
                        DOMResourceLoadList.innerHeight() + this.$class.WATERMARK_MARGIN_BOTTOM,
                        this.$class.WATERMARK_MIN_HEIGHT));
            }
        },
        adjustTimeTrackerSize : function() {
            this.recalculateTimeTrackerHeight();

            /*
            * We can't use this.getHeight() as the _height property won't be set for this object and even when it changes 
            * (recalculateTimeTrackerHeight) so, we avoid using DOM selectors.
            * TODO: maybe create a _height property and update it
            */
            jq('#watermark').height(jq(this.$n()).innerHeight());
            
            jq(this.$n()).width(jq('#timetracker .z-vbox').innerWidth());
        },
        adjustResourceLoadRows : function() {
            jq(this.$n()).width(jq('#timetracker .z-vbox').innerWidth());
        },
        _listenToScroll : function(){
            var scrolledPannelScrollLeft = jq('.rightpanel-layout div:first').scrollLeft();
            var scrolledPannelScrollTop = jq('.rightpanel-layout div:first').scrollTop();

            jq('canvas.timeplot-canvas').parent().css("left", "-" + scrolledPannelScrollLeft + "px");
            jq('.timetrackergap').css("left", "-" + scrolledPannelScrollLeft + "px");
            jq('.leftpanelgap .z-tree-body').css("top", "-" + scrolledPannelScrollTop + "px");
            jq('.resourcesloadgraph div').scrollLeft(scrolledPannelScrollLeft + "px");

            this.adjustResourceLoadRows();
        }
    },
    {
        //Class stuff
        WATERMARK_MIN_HEIGHT : 450,
        WATERMARK_MARGIN_BOTTOM : 40,
        setInstance : function(instance){
            this._instance = instance;
        },
        getInstance : function(){
            return this._instance;
        }
    });