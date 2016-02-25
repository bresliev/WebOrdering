<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!-- Second, add the Timer and Easing plugins -->
<script type="text/javascript" src="js/gallery/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="js/gallery/jquery.easing.1.3.js"></script>

<!-- Third, add the GalleryView Javascript and CSS files -->
<script type="text/javascript" src="js/gallery/jquery.galleryview-3.0-dev.js"></script>
<link type="text/css" rel="stylesheet" href="css/jquery.galleryview-3.0-dev.css"/>

<!-- Lastly, call the galleryView() function on your unordered list(s) -->
<script type="text/javascript">
    $(function () {
        $('.myGallery').galleryView({
            transition_speed:1000, //INT - duration of panel/frame transition (in milliseconds)
            transition_interval:2000, //INT - delay between panel/frame transitions (in milliseconds)
            easing:'swing', //STRING - easing method to use for animations (jQuery provides 'swing' or 'linear', more available with jQuery UI or Easing plugin)
            show_panels:true, //BOOLEAN - flag to show or hide panel portion of gallery
            show_panel_nav:true, //BOOLEAN - flag to show or hide panel navigation buttons
            enable_overlays:true, //BOOLEAN - flag to show or hide panel overlays

            panel_width:700, //INT - width of gallery panel (in pixels)
            panel_height:490, //INT - height of gallery panel (in pixels)
            panel_animation:'crossfade', //STRING - animation method for panel transitions (crossfade,fade,slide,none)
            panel_scale:'crop', //STRING - cropping option for panel images (crop = scale image and fit to aspect ratio determined by panel_width and panel_height, fit = scale image and preserve original aspect ratio)
            overlay_position:'bottom', //STRING - position of panel overlay (bottom, top)
            pan_images:true, //BOOLEAN - flag to allow user to grab/drag oversized images within gallery
            pan_style:'drag', //STRING - panning method (drag = user clicks and drags image to pan, track = image automatically pans based on mouse position
            pan_smoothness:15, //INT - determines smoothness of tracking pan animation (higher number = smoother)
            start_frame:1, //INT - index of panel/frame to show first when gallery loads
            show_filmstrip:true, //BOOLEAN - flag to show or hide filmstrip portion of gallery
            show_filmstrip_nav:true, //BOOLEAN - flag indicating whether to display navigation buttons
            enable_slideshow:true, //BOOLEAN - flag indicating whether to display slideshow play/pause button
            autoplay:false, //BOOLEAN - flag to start slideshow on gallery load
            show_captions:false, //BOOLEAN - flag to show or hide frame captions
            filmstrip_size:3, //INT - number of frames to show in filmstrip-only gallery
            filmstrip_style:'scroll', //STRING - type of filmstrip to use (scroll = display one line of frames, scroll filmstrip if necessary, showall = display multiple rows of frames if necessary)
            filmstrip_position:'bottom', //STRING - position of filmstrip within gallery (bottom, top, left, right)
            frame_width:120, //INT - width of filmstrip frames (in pixels)
            frame_height:84, //INT - width of filmstrip frames (in pixels)
            frame_opacity:0.5, //FLOAT - transparency of non-active frames (1.0 = opaque, 0.0 = transparent)
            frame_scale:'crop', //STRING - cropping option for filmstrip images (same as above)
            frame_gap:5, //INT - spacing between frames within filmstrip (in pixels)
            show_infobar:true, //BOOLEAN - flag to show or hide infobar
            infobar_opacity:1                //FLOAT - transparency for info bar
        });
    });
</script>
<style type="text/css">
    .ui-dialog-titlebar {
        display: none;
    }

    .ui-widget-content {
        border: none;
        background: none;
    }

    .gv_overlay, .gv_showOverlay {
        display: none;
    }
</style>

<c:import var="xmlDoc" url="http://10.10.10.18:8080/WO/oprema.xml"/>

<x:parse var="parsedDocument" xml="${xmlDoc}"/>

<div class="content">
    <div class="navigation_bar_left dark_gray_gradient" style="width:622px;">
        <h3>Oprema i Vozila</h3>
    </div>
    <div class="navigation_bar_right" style="width:64px;"><img src="images/strelice.png" alt=""/></div>

    <x:forEach select="$parsedDocument/opreme/oprema">
        <x:set var="id" select="@id"/>
        <div style="width:716px;height:159px;float:left;border-bottom:solid 1px #bbbbbb;margin:10px 0 0 10px;">
            <div style="float:left;padding:8px;width:470px;">
                <div style="color:#070707;font-size:14px;font-weight:bold;"><x:out select="naziv"/></div>
                <div class="multiline" style="color:#666666;font-size:11px;margin-top:10px;height:48px;line-height:12px;"><x:out select="opis"/></div>
                <div style="color:#e47818;font-size:13px;font-weight:bold;;margin-top:10px;">CENA: <x:out select="cena"/></div>
                <div style="color:#666666;font-size:11px;margin-top:10px;"><x:out select="kontakt"/></div>
            </div>
            <img id="pro-<x:out select='@id'/>" style="float:right;border:solid 1px #bbbbbb;" class="product_item_img opener" src="<x:out select='imgDefault'/>" alt="">

            <div class="dialogMultiImg" id="opener-pro-<x:out select='@id'/>">
                <ul class="myGallery">
                    <x:forEach select="$parsedDocument/opreme/oprema[@id=$id]/img">
                           <li><img data-frame="<x:out select='@small'/>" src="<x:out select='@large'/>" title="" data-description=""/>
                    </x:forEach>
                </ul>
            </div>
        </div>
    </x:forEach>
</div>