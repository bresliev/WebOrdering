var pageTotalNum = 0;
var timer;
// increase the default animation speed to exaggerate the effect
$.fx.speeds._default = 1000;

$.ajaxSetup({
    cache: false,
    statusCode: {
    901:function () {
            clearTimeout(timer);
            $(".error-box").text("Vaša sesija je istekla. Ukoliko ste imali nepotvrðene artikle u korpi sve stavke æe biti poništene. ");
            $("#dialog-message-w").dialog("open");
        }
    }
});


(function ($) {
    $.fn.showGroups = function () {
        $('.waitcursor').show();
        $('.group_item').fadeTo(500, 0.2);
        var formAction = $('#formAction').attr('value');
        $(this).createTopNavMenuG2($("#formAction").attr('value'));
        $('#formActionLabel').text(formAction);
        var group = "<div id='groups' style='width:736px;'>";
        console.log("ovo je akcija forme "+formAction);
        $.ajax({
            cache: false,
            url: 'getGroupsJSON',
            data: {
                formAction: formAction
            },
            dataType: 'json',
            success: function (result) {
                if (result) {
                    console.log("Uspesan poziv getGroupsJSON ");
                    $.each(result, function (i, item) {
                        console.log("Klasa " + item.naziv);
                        group += $(this).createGroupItemDiv(item);
                    });
                }
                group += "</div>";
                $("#navigation_bar").remove();
                if ($("#products").length) $("#products").remove();
                if ($("#groups").length) $("#groups").remove();
                $('.content').append(group);
                $('.group_item').fadeIn(2000);
                $('.waitcursor').hide();
            }
        });
    };


    $.fn.createGroupItemDiv = function (item) {
        console.log("Klasa" + item.naziv);
        var group = "<div class='group_item dark_gray_gradient' style='display: none;'>" +
            "<div class='group_name'>" + item.naziv + "</div>" +
            "<img id='dialog-img-" + item.id.klasifikacija + "' class='product_item_img' src='/WO/images/klasifikacija/" + item.id.klasifikacija + ".jpg' alt=''/>"  +
            "</div>";
        return group;
    };

    $("#groups .group_item .product_item_img").live('click', function () {
        var klasa = $(this).attr('id');
        $('#brandId').attr('value', klasa.substring(11, klasa.length));
        $('.group_item').fadeTo(500, 0.2);
        $(this).showProducts(0,"");
    });


    $.fn.showProducts = function (pageNo, productId) {
        try {
           $('.waitcursor').show();
            $('.product_item').fadeTo(500, 0.2);
            $("#groups").fadeTo(500, 0.2);
            var namePattern = "";
            var formAction = $('#formAction').attr('value');
            if (formAction == 'FILTER' && $("#namePattern").attr('value').length > 0
                && $("#namePattern").attr('value') != 'NAZIV ARTIKLA') namePattern = $("#namePattern").attr('value');
            $(this).createTopNavMenu($("#formAction").attr('value'));
            if (formAction != 'MENI') $('#formActionLabel').text(formAction);
            if (formAction == 'Oprema/Vozila') formAction = 'MENI';
            else if ($('#formActionLabel').text() == "") $('#formActionLabel').text($('#formActionLabelTemp').val());
            var products = "<div id='products' style='width:736px;'>";
            var rowCount = 0;
            var perPage = $("#perPage").attr('value');
            $.ajax({
                cache: false,
                url: 'getProductsJSON',
                data: {
                    formAction: formAction,
                    brandId: $("#brandId").attr('value'),
                    pageNo: pageNo,
                    perPage: perPage,
                    namePattern: namePattern,
                    productId: productId
                },
                dataType: 'json',
                success: function (result) {
                    if (result) {
                        rowCount = result.rowCount;
                        $.each(result.proizvodiList, function (i, item) {
                            products += $(this).createProductItemDiv(item);
                        });
                        if (pageNo == 0 && rowCount == 0) {
                            products += "<div class='msg_div'><img src='images/error.png' alt='' style='vertical-align:middle;'/>&nbsp;&nbsp;Izabrani kriterijum pretrage nije vratio nijedan rezultat!</div>";
                        }
                    }
                    products += "</div>";// end product

                    if (pageNo == 0 && rowCount > perPage) {
                        if (rowCount % perPage == 0) pageTotalNum = parseInt(rowCount / perPage);
                        else pageTotalNum = parseInt(rowCount / perPage) + 1;
                        $("#products").remove();
                        $("#groups").remove();
                        $("#navigation_bar").remove();
                        products += $(this).createDownNavMenu();
                        $(".content").append(products);
                    } else if (pageNo == 0) {
                        $("#navigation_bar").remove();
                        $("#groups").remove();
                        if ($("#products").length) $("#products").replaceWith(products);
                        else $('.content').append(products);
                        /*} else if (rowCount > perPage) {
                         $("#products").replaceWith(products);   */
                    } else {
                        $("#groups").remove();
                        $("#products").replaceWith(products);
                    }
                    $(this).dialogCfg();
                    $('#navigation_bar').show();
                    $('.product_item').fadeIn(2000);
                    $('.waitcursor').hide();
                    $("html, body").animate({scrollTop: $(".G4").offset().top - 10}, 'slow');
                }
            });
        } catch (e) {
            $('.waitcursor').hide();
        }
    };

    $.fn.createProductItemDiv = function (item) {
        var tipAkcijeImg = "";
        var raspolozivoPoPak = item.raspolozivo;
        var zalihe = "";
        if (item.tipAkcije == 'AKCIJA' || item.tipAkcije == 'IZDVOJENA_AKCIJA') tipAkcijeImg = "<img src='images/Akcije.png' alt='' style='position: absolute;top: 34px;right: 11px;'>";
        else if (item.tipAkcije == 'NOVO') tipAkcijeImg = "<img src='images/Novo.png' alt='' style='position: absolute;top: 34px;right: 11px;'>";
        else if (item.tipAkcije == 'AKTUELNO') tipAkcijeImg = "<img src='images/Aktuelno.png' alt='' style='position: absolute;top: 34px;right: 11px;'>";
        else if (item.tipAkcije == 'RASPRODAJA') tipAkcijeImg = "<img src='images/Rasprodaja.png' alt='' style='position: absolute;top: 34px;right: 11px;'>";
        var product = "<div class='product_item' style='display: none;'>" +
            "<div class='dezen'>" + item.dezenIstruktira + "</div>" +
            "<img id='dialog-img-" + item.proizvod + "' class='product_item_img opener' src='/WO/images/medium/" + item.proizvod + ".jpg' alt=''/>" + tipAkcijeImg +
            "<div style='width:220px;padding:0 5px 0 5px;border-bottom: 1px solid #b3b3b3;'>" +
            "<div class='cena'>" + item.cena + " eur/" + item.jedinicaMere.skracenaOznaka.toLowerCase() + "</div>" +
            "<div class='proizvodjac ellipsis'>" + item.proizvod + " - " + item.proizvodjac + "</div>" +
            "<div class='ime multiline'>" + item.nazivProizvoda + " " + $(this).trimToEmptyString(item.dodatniNaziv) + "</div>" +
            "</div>";

        if (item.primeniJsklPakovanje) {
            var pakovanja = "<select id='pakovanje-" + item.proizvod + "' style='border:solid 1px #666666;width:85px;'>";
            $.each(item.jsklPakovanja, function (i, pak) {
                console.log("ima vise pakovanja pro=" + item.proizvod + " pak=" + pak);
                pakovanja += "<option value='" + pak + "'>" + item.brojPakovanja[pak].brojPakovanja + " x " + pak + " " + item.jedinicaMere.skracenaOznaka.toLowerCase() + "</option> "
            });
            pakovanja += "</select>";
            product += "<div class='kol_pak ellipsis'>Kolièina po pakovanju " + pakovanja + "</div>";
            raspolozivoPoPak = item.raspolozivoPerPak;
            zalihe = item.raspolozivo + " " + item.jedinicaMere.skracenaOznaka;
        }
        /*else if (item.primeniJsklPakovanje && item.jsklPakovanja.length == 1) {
            //console.log("ima jedno pakovanja pro=" + item.proizvod + " pak=" + item.jsklPakovanja[0]);
            product += "<div class='kol_pak ellipsis'>Kolièina po pakovanju: " + item.jsklPakovanja[0] + " " + item.jedinicaMere.skracenaOznaka.toLowerCase() + "</div>";
            zalihe = item.raspolozivo + " " + item.jedinicaMere.skracenaOznaka;
        } */
        //
        else {
            zalihe = Math.ceil(item.raspolozivo/item.kolicinaPoPakovanju) + ' ' + item.jedinicaMereAltRef.skracenaOznaka + ' / ' + item.raspolozivo + ' ' + item.jedinicaMere.skracenaOznaka;
            product += "<div class='kol_pak ellipsis'>Kolièina po pakovanju: " + item.kolicinaPoPakovanju + " " + item.jedinicaMere.skracenaOznaka.toLowerCase() + "</div>";
        }

        //+" ("+ raspolozivoPoPak +")"
        //console.log("radi formiranje pro");
        product += "<div class='kolicina'>Poruèiti: <input type='number' min='1' step='1' id='orderedQuantity-" + item.proizvod + "' value=''/>" + item.jedinicaMereAltRef.skracenaOznaka.toLowerCase() + "</div>" +
            "<div class='zalihe ellipsis' id='raspolozivo-" + item.proizvod + "'>Zalihe: " + zalihe + "</div>" +
            "<div class='potvrda'>" +
            "<div style='float: left;'><input type='submit' id='dialog-" + item.proizvod + "' class='btn zoom opener'  value='UVEÆAJ' style='background-color:#b2b2b2;'/></div>" +
            "<div style='float: right;'><input type='submit' id='basket-" + item.proizvod + "' class='btn basket add_to_basket' value='DODAJ U KORPU'/></div>" +
            "</div>" +
            "<div class='dialog' id='opener-dialog-" + item.proizvod + "' title='" + item.nazivProizvoda + " " + $(this).trimToEmptyString(item.dodatniNaziv) + "'>" +
            "<img style='margin:0px;border:none;' width='700px' height='490px' src='/WO/images/large/" + item.proizvod + ".jpg' alt='' />" +
            "</div>" +
            "</div>";

        return product;
    };

    $.fn.createTopNavMenu = function (formAction) {
        var topMenu = $("#navigation_bar_top");

        //console.log("evo ga meni "+topMenu.length);
        if (topMenu.length && $('#selectPerPage').length);
        else {
            var perPage = $("#perPage").val();
            topMenu = "<div id='navigation_bar_top'>" +
                "<div class='navigation_bar_left dark_gray_gradient'>" +
                "<h3 id='formActionLabel'></h3>" +
                "</div>" +
                "<div class='navigation_bar_right' style='display: inline;'>" +
                "Broj artikala po strani:" +
                "<select style='margin:0;' id='selectPerPage'>" +
                "<option value='3' " + (perPage == 3 ? "selected" : "") + ">3</option>" +
                "<option value='6' " + (perPage == 6 ? "selected" : "") + ">6</option>" +
                "<option value='9' " + (perPage == 9 ? "selected" : "") + ">9</option>" +
                "<option value='15' " + (perPage == 15 ? "selected" : "") + ">15</option>" +
                "<option value='10000' " + (perPage == 'sve' ? "selected" : "") + ">sve</option>" +
                "</select>" +
                "</div></div>";
            $('.content').empty();
            $(".content").append(topMenu);


        }
    };

    $.fn.createTopNavMenuG2 = function (formAction) {
        var topMenu = $("#navigation_bar_top");

        //console.log("evo ga meni G2 "+topMenu.length);
        if (topMenu.length && !$('#selectPerPage').length);
        else {
            var perPage = $("#perPage").val();
            topMenu = "<div id='navigation_bar_top'>" +
                "<div class='navigation_bar_left dark_gray_gradient'>" +
                "<h3 id='formActionLabel'></h3>" +
                "</div>" +
                "<div class='navigation_bar_right' style='display: inline;'>" +
                "</div>" +
                "</div>";
            $('.content').empty();
            $(".content").append(topMenu);
        }
    };

    $.fn.createDownNavMenu = function () {
        var navButtons = "";

        var index = (pageTotalNum < 6) ? pageTotalNum : 6;
        for (var i = 1; i <= index; i++) {
            if (i == 1) navButtons += "<input type='button' class='navigation_bar btn light_gray_gradient selected' value='" + i + "'/>";
            else navButtons += "<input type='button' class='navigation_bar btn light_gray_gradient' value='" + i + "'/>";
        }
        return  "<div id='navigation_bar'>" +
            "<div class='navigation_bar_left dark_gray_gradient' style='margin-top:10px;'>" +
            "<input type='button' class='btn light_gray_gradient' value='<<'/>" +
            "<input type='button' class='btn light_gray_gradient' value='<'/>" +
            "<div id='navigation_buttons' style='display: inline;'>" +
            navButtons +
            "</div>" +
            "<input type='button' class='btn light_gray_gradient' value='>'/>" +
            "<input type='button' class='btn light_gray_gradient' value='>>'/>" +
            "<img class='waitcursor' src='images/wait16_orange.gif'/>" +
            "</div>" +
            "<div class='navigation_bar_right' style='margin-top:10px;'>" +
            "Strana: <div id='current_page' style='display: inline;'>1/" + pageTotalNum + "</div>" +
            "</div>" +
            "</div>";
    };
    $.fn.refreshDownNavMenu = function (currentPage, actionPrevNext) {
        var first = $('.navigation_bar:first').attr('value');
        var last = $('.navigation_bar:last').attr('value');
        var currPage = 1;
        if (actionPrevNext == ">" && currentPage == (parseInt(last) + 1)) currPage = parseInt(currentPage) - 1;
        else if (actionPrevNext == "<" && currentPage == (parseInt(first) - 1)) currPage = parseInt(currentPage) + 1;
        else currPage = currentPage;
        if ((currPage == first && first > 1) || (currPage == last && last < pageTotalNum)) {
            var navButtons = "<div id='navigation_buttons' style='display: inline;'>";
            if (currPage == last) {
                for (var i = currPage; i <= currPage + 5; i++) {
                    if (i <= pageTotalNum) navButtons += "<input type='button' class='navigation_bar btn light_gray_gradient' value='" + i + "'/>";
                }
            } else if (currPage == first) {
                for (var i = currPage - 5; i <= currPage; i++) {
                    if (i >= 1) navButtons += "<input type='button' class='navigation_bar btn light_gray_gradient' value='" + i + "'/>";
                }
            }
            navButtons += "</div>";
            $("#navigation_buttons").replaceWith(navButtons);
        } else if (actionPrevNext == '>>') {
            var start = 0;
            var temp = (parseInt(pageTotalNum) % 5);
            if (temp == 0 || temp == 1) start = pageTotalNum - temp - 4;
            else start = pageTotalNum + 1 - temp;
            if (pageTotalNum <= 6) start = 1;
            var navButtons = "<div id='navigation_buttons' style='display: inline;'>";
            for (var i = start; i <= pageTotalNum; i++) navButtons += "<input type='button' class='navigation_bar btn light_gray_gradient' value='" + i + "'/>";
            navButtons += "</div>";
            $("#navigation_buttons").replaceWith(navButtons);
        }
        $("#navigation_bar input").removeClass("selected");
        $("input[type='button'][value='" + currentPage + "']").addClass("selected");
        if (currentPage != parseInt($("#current_page").text().split("/")[0]))
            $(this).showProducts(currentPage - 1, "");
        $("#current_page").text(currentPage + "/" + pageTotalNum);
    };
    $.fn.dialogCfg = function () {
        $(".dialog, .dialogMultiImg").dialog({
            /*width:700,*/
            width: "auto",
            height: "auto",
            autoOpen: false,
            show: "explode",
            hide: "explode",
            modal: true,
            draggable: false,
            position: 'center',
            resizable: false
        });
        $(".ui-dialog-content").css('padding', '0');

    };
    $.fn.dialogCfgMSG = function () {
        $("#dialog-message,#dialog-message-w,#dialog-message-i").dialog({
            width: "320px",
            height: "auto",
            autoOpen: false,
            show: "explode",
            hide: "explode",
            modal: true,
            resizable: false,
            position: 'center',
            buttons: {
                OK: function () {
                    $(this).dialog("close");
                    if ($(this).attr('id') == 'dialog-message-w') {
                        window.location = "/WebOrdering/login";
                    }
                }
            }
        });
        $(".ui-dialog-content").css('padding', '0');

    };

    $.fn.ellipsis = function () {
        return this.each(function () {
            var el = $(this);

            if (el.css("overflow") == "hidden") {
                var text = el.html();
                var multiline = el.hasClass('multiline');
                var t = $(this.cloneNode(true))
                    .hide()
                    .css('position', 'absolute')
                    .css('overflow', 'visible')
                    .width(multiline ? el.width() : 'auto')
                    .height(multiline ? 'auto' : el.height());
                el.after(t);
                function height() {
                    return t.height() > el.height();
                }
                function width() {
                    return t.width() > el.width();
                }
                var func = multiline ? height : width;

                while (text.length > 0 && func()) {
                    text = text.substr(0, text.length - 1);
                    t.html(text + "...");
                }
                el.html(t.html());
                t.remove();
            }
        });
    };
    $.fn.resetMenu = function () {
        $('.opened').removeClass('opened');
        $(".side_menu ul li").siblings().children('ul').fadeOut(500);
        $(".selected").removeClass("selected");
    };
    $.fn.resetFilter = function () {
        $('#namePattern').val('NAZIV ARTIKLA');
        $(".filterPrviNivo").prop('selectedIndex', 0);
        $(".filterDrugiNivo").prop('selectedIndex', 0);
        $(".selected").removeClass("selected");
    };

    $.fn.trimToEmptyString = function (value) {
        if (value == null || value == 'undefined') return "";
        else return value;
    };

    $.fn.showNajprodavanije = function () {
        $.ajax({
            cache: false,
            url: 'getNajprodavanije',
            dataType: 'json',
            success: function (result) {
                if (result) {
                    var resDiv = "<div id='div-najprodavanije' style='display: none;'>";
                    $.each(result, function (i, item) {
                        //console.log(item.proizvod + "   " + item.cena + "   " + i);
                        resDiv += "<div id='najprodavanije-" + item.proizvod + "' class='fade najprod'>" +
                            /*"<img width='120px' height='84px' src='http://slikeproizvoda.darex.rs/slike/" + item.proizvod + ".jpg'  alt=''/>" +*/
                            "<img width='120px' height='84px' src='/WO/images/small/" + item.proizvod + ".jpg'  alt=''/>" +
                            "<div class='naziv multiline'>" + item.nazivProizvoda + " " + $(this).trimToEmptyString(item.dodatniNaziv) + "</div>" +
                            "<div class='cena'>" + item.cena + " EUR</div>" +
                            "</div>";
                        if (i < 3) resDiv += "<hr class='thin' style='margin:10px;'>";
                    });
                    $("#div-najprodavanije").fadeOut(500);
                    $("#div-najprodavanije").replaceWith(resDiv);
                    $("#div-najprodavanije").fadeIn(500);


                }
            }
        });
        timer = setTimeout($(this).showNajprodavanije, 15000);
    };
})(jQuery);


$(document).ready(function () {
    if ($.browser.msie && parseInt($.browser.version, 10) <= 7) {
        $("input, select").css('vertical-align', 'middle');
        $("#partnerPretraga").css('margin-top', '8px');
    }
    setTimeout($(this).showNajprodavanije, 15000);
    $(this).dialogCfg();
    $(this).dialogCfgMSG();
    $(".ellipsis").ellipsis();
    $(".multiline").ellipsis();

    $(window).scroll(function () {
        if ($(this).scrollTop() != 0) {
            $('#toTop').fadeIn();
        } else {
            $('#toTop').fadeOut();
        }
    });

    $('#toTop').live('click', function () {
        $('body,html').animate({scrollTop: 0}, 'slow');
    });

    $(".side_menu ul li").live('click', function () {
        if ($(this).parents('li').length == 0) {
            //$('ul li ul li.opened ul li').fadeOut(500);
        }

        $(this).resetFilter();
        $("#productId").val("ŠIFRA");
        $('.opened').removeClass('opened');
        $(this).addClass('opened');
        $(this).siblings().children('ul').fadeOut(500);
        $(this).children('ul').slideToggle(500);

        if ($(this).children('ul').length > 0) {
            $('#formActionLabel').text($(this).children("a:first").text());
            $('#formActionLabelTemp').val($(this).children("a:first").text());
        } else {
            var brandNAme = "";
            $(this).parents('li').each(function () {
                brandNAme += $(this).children("a:first").text() + " - ";
            });
            brandNAme += $(this).children("a:first").text();
            $('#formActionLabel').text(brandNAme);
            $('#formActionLabelTemp').val(brandNAme);
            $('#brandId').attr('value', $(this).attr('id'));
            $('#formAction').attr('value', 'MENI');
            $(this).showProducts(0, "");
        }
        return false;
    });
    $("#navigation_bar input").live('click', function () {
        /*$("#navigation_bar input").removeClass("selected");
         $(this).addClass("selected");*/
        if ($(this).val() == "<<") $(this).refreshDownNavMenu(1, "<<");
        else if ($(this).val() == ">>") $(this).refreshDownNavMenu(pageTotalNum, ">>");
        else {
            var currentPage = parseInt($("#current_page").text().split("/")[0]);
            if ($(this).val() == "<" && currentPage > 1) $(this).refreshDownNavMenu(currentPage - 1, "<");
            else if ($(this).val() == ">" && currentPage < pageTotalNum) $(this).refreshDownNavMenu(currentPage + 1, ">");
        }
    });
    $(".navigation_bar").live('click', function () {
        var currentPage = parseInt($(this).attr('value'));
        $(this).refreshDownNavMenu(currentPage, "");
    });

    $(".G2_MENI").live('click', function () {
        $(this).resetFilter();
        $(this).resetMenu();
        $("#productId").val("ŠIFRA");
        $(this).addClass("selected");
        $('#formAction').attr('value', $(this).attr('id'));
        $(this).showGroups();
        //$(this).showProducts(0, "");
    });
    $(".G2_MENI_NEW").live('click', function () {
        $(this).resetFilter();
        $(this).resetMenu();
        $("#productId").val("ŠIFRA");
        $(this).addClass("selected");
        $('#formAction').attr('value', $(this).text());
        $('#brandId').attr('value', $(this).attr('id'));
        $(this).showProducts(0, "");
    });

    /*
     $(".product_item_img").live('mouseenter', function() {
     // mouse-over
     $("#" + $(this).attr('id') + "-1").fadeOut(500);
     $("#" + $(this).attr('id') + "-2").fadeOut(500);
     });

     $(".product_item_img").live('mouseleave', function() {
     // mouse-out
     $("#" + $(this).attr('id') + "-1").fadeIn(500);
     $("#" + $(this).attr('id') + "-2").fadeIn(500);
     });*/


    $(".opener").live('click', function () {
        var id = $(this).attr('id');
        id = id.replace("-img", "");
        $('#opener-' + id).dialog("open");
        return false;
    });

    $('.dialog img').live('click', function () {
        $(this).parents(".ui-dialog-content").dialog('close');
    });

    $('.dialogMultiImg .gv_panel img').live('click', function () {
        $(this).parents(".ui-dialog-content").dialog('close');
    });

    $("#selectPerPage").live('change', function () {
        $("#perPage").attr('value', $(this).attr('value'));
        $(this).showProducts(0, "");
    });

    $("select.filterPrviNivo").live('change', function () {
        $(this).resetMenu();
        $("#productId").val("ŠIFRA");
        var res = "<select style='width: 170px;' class='filterDrugiNivo'>";
        res += "<option value='-1'>-- GRUPA --</option>";
        if ($(this).attr('value') != '-1') {
            $.getJSON("getFilterDrugiNivo", {
                cache: false,
                brandId: $(this).attr('value')
            }, function (results) {
                if (results) {
                    $.each(results, function (i, item) {
                        res += "<option value='" + item.id.klasifikacija + "' >" + item.naziv + "</option>";
                    });
                }
                res += "</select>";
                $(".filterDrugiNivo").replaceWith(res);
                if ($.browser.msie && parseInt($.browser.version, 10) <= 7) {
                    $(".filterDrugiNivo").css('vertical-align', 'middle');
                }
            });
        } else {
            $('#brandId').attr('value', null);
            res += "</select>";
            $(".filterDrugiNivo").replaceWith(res);
        }
    });
    $("select.filterDrugiNivo").live('change', function () {
        $(this).resetMenu();
        $("#productId").val("ŠIFRA");
    });

    $("#search_button").live('click', function () {
        try {
            $(this).resetMenu();
            $('#brandId').attr('value', null);
            var prvi = $(".filterPrviNivo option:selected").val();
            var drugi = $(".filterDrugiNivo option:selected").val();
            var search = true;
            if (drugi != "-1") {
                $('#brandId').attr('value', drugi);
            } else if (prvi != "-1") {
                $('#brandId').attr('value', prvi.split("/")[1]);
            } else search = false;
            if (search || ($('#namePattern').val() != '' && $('#namePattern').val() != "NAZIV ARTIKLA")) {
                $('#formAction').attr('value', 'FILTER');
                $(this).showProducts(0, "");
            } else if (parseInt($.trim($('#productId').val())) > 0) {
                $('#formAction').attr('value', $('#productId').val());
                $(this).showProducts(0, $.trim($('#productId').val()));
            }
        } catch (e) {
            //console.log(e)
        }
    });

    $("#productId").live('keypress', function () {
        $(this).resetFilter();
        $(this).resetMenu();
    });

    $("#productId").live('keydown', function (e) {
        if (e.keyCode == 13) {
            $("#search_button").click();
        }
    });

    $("#namePattern").autocomplete({
        source: function (request, response) {
            var prvi = $(".filterPrviNivo option:selected").val();
            var drugi = $(".filterDrugiNivo option:selected").val();
            if (drugi != "-1") $('#brandId').attr('value', drugi);
            else if (prvi != "-1") $('#brandId').attr('value', prvi.split("/")[1]);
            else $('#brandId').attr('value', null);
            $(this).resetMenu();
            $("#productId").val("ŠIFRA");
            $.ajax({
                cache: false,
                url: "getProductsByName",
                dataType: "json",
                data: {
                    term: request.term,
                    brandId: $('#brandId').attr('value')

                },
                success: function (data) {
                    response($.map(data.products, function (item) {
                        return {
                            id: item.proizvod,
                            label: item.nazivProizvoda + " " + $(this).trimToEmptyString(item.dodatniNaziv),
                            value: ""
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
            $('#formAction').attr('value', ui.item.label);
            $(this).showProducts(0, ui.item.id);
        }

    });

    $("#namePattern").live('keydown', function (e) {
        if (e.keyCode == 13) {
            $(".ui-autocomplete").hide();
            $("#search_button").click();
        }
    });

    $("#partnerPretraga").autocomplete({
        source: function (request, response) {
            $.ajax({
                cache: false,
                url: "getPartnersByName",
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data.partners, function (item) {
                        return {
                            id: item.poslovniPartner,
                            label: item.naziv,
                            value: ""
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
            $("#partnerId").val(ui.item.id);
            $("#changePartnerForm").submit()
        }
    });

    $(".add_to_basket").live('click', function () {
        try {
            var btn = $(this);
            var productId = $(this).attr('id').replace("basket-", "");
            var pakovanje = $("#pakovanje-" + productId + " :selected").val();
            var orderedQuantity = $("#orderedQuantity-" + productId).val();
            var zalihe = "";
            if ($.trim(orderedQuantity) == '' || parseFloat(orderedQuantity) <= 0 || orderedQuantity%Math.floor(orderedQuantity) != 0) {
                $(".error-box").text("Odabrani proizvod nije moguæe dodati u korpu. Proverite unetu kolièinu!");
                $("#dialog-message").dialog("open");
            }
            else {
                btn.removeClass('basket');
                btn.addClass('wait');
                $.getJSON("addToBasket", {
                        cache: false,
                        productId: productId,
                        orderedQuantity: parseFloat(orderedQuantity),
                        pakovanje: pakovanje
                    }, function (result) {
                        if (result && !result.errorMsg) {

                            if (result.primeniJsklPakovanje) {
                                var pakovanja = " ";
                                $.each(result.jsklPakovanja, function (i, pak) {
                                    pakovanja += "<option value='" + pak + "'>" + result.brojPakovanja[pak].brojPakovanja + " x " + pak + " " + result.jm.toLowerCase() + "</option> ";
                                });
                                $('#pakovanje-' + productId).empty();
                                $('#pakovanje-' + productId).append(pakovanja);
                                zalihe = result.stanjeZaliha + ' ' + result.jm;
                            } else {
                                zalihe = Math.ceil(result.stanjeZalihaAlt) + ' ' + result.jmAlt + ' / ' + result.stanjeZaliha + ' ' + result.jm;
                            }
                            $('#raspolozivo-' + productId).text('Zalihe: ' + zalihe);
                            $('#broj_stavki').text(result.brojStavki);
                            $('#ukupna_cena').text(result.ukupnaCena + " EUR");
                        } else {
                            if (result.primeniJsklPakovanje) {
                                zalihe = result.stanjeZaliha + ' ' + result.jm;
                            } else {
                                zalihe = Math.ceil(result.stanjeZalihaAlt) + ' ' + result.jmAlt + ' / ' + result.stanjeZaliha + ' ' + result.jm;
                            }
                            $('#raspolozivo-' + productId).text('Zalihe: ' + zalihe);
                            $(".error-box").text("Odabrani proizvod nije moguæe dodati u korpu. Proverite stanje na zalihama sa Vašom službom prodaje.");
                            $("#dialog-message").dialog("open");
                        }
                        $("#orderedQuantity-" + productId).val("");
                        btn.removeClass('wait');
                        btn.addClass('basket');
                    }
                );
            }
        } catch (e) {
            btn.removeClass('wait');
            btn.addClass('basket');
            $(".error-box").text("Odabrani proizvod nije moguæe dodati u korpu. Proverite stanje na zalihama sa Vašom službom prodaje.");
            $("#dialog-message").dialog("open");
        }
    });

    $(".product_item .kolicina input").live('keydown', function (e) {
        var productId = $(this).attr('id').replace("orderedQuantity-", "");
        if (e.keyCode == 13) {
            $("#basket-" + productId).click();
        }
    });


    $('.najprod').live('click', function () {
        $(this).resetMenu();
        $(this).resetFilter();
        var productId = $(this).attr('id').replace("najprodavanije-", "");
        $('#formAction').attr('value', 'NAJPRODAVANIJE');
        $(this).showProducts(0, productId);

    });

    $("#karticaPKP").live('click', function () {
        //$('#karticaHidden').datepicker('show');
        window.open('kartica');
    });

    $("#kartica").live('click', function () {
        $(".error-box").text("Nemate pravo na pregled kartice!");
        $("#dialog-message").dialog("open");
    });

    /*$("#karticaHidden").datepicker({
     dateFormat : 'yy-mm-dd',
     monthNames: ['Januar', 'Februar', 'Mart', 'April', 'Maj', 'Jun', 'Jul', 'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar'],
     dayNamesMin: ['Ne', 'Po', 'Ut', 'Sr', '?e', 'Pe', 'Su'],
     onClose: function(dateText) {
     if (dateText != "") window.open('kartica?datumDo=' + dateText);
     $("#kartica1").val('');
     }
     });*/

    $(".title_div div .btn").hover(function () {
        $(this).parent("div").prev(".download").css('color', '#f2af26')
    }, function () {
        $(this).parent("div").prev(".download").css('color', '#ffffff')
    });
    /*
     $("#div-najprodavanije .fade").hover(function(){
     $(this).fadeTo(400, 0.4);
     }, function(){
     $(this).fadeTo(400, 1);
     });  */

    $("#div-najprodavanije .fade").live('mouseenter', function () {
        // mouse-over
        $(this).fadeTo(400, 0.4);
    });

    $("#div-najprodavanije .fade").live('mouseleave', function () {
        // mouse-out
        $(this).fadeTo(400, 1);
    });

    $("table.korpa tr td:not(:last-child)").live('click', function () {
        if (!$(this).hasClass("not_link")) {
            var productId = $(this).parent('tr').attr('id').replace("korpa_uvecaj_", "");
            $('#uvecaj_' + productId).dialog("open");
            return false;
        }
    });

    $("#formFakture a").live('click', function () {
        if ($("#userRight").val() == "true") {
            $("#idDokumenta").val($(this).text());
            $("#formFakture").submit();
        } else {
            $(".error-box").text("Nemate pravo na pregled predraèuna!");
            $("#dialog-message").dialog("open");
        }
    });

    $("#chceckOutBasketBtn").live('click', function () {
        if (!$("input[name='nacinTransporta']:checked").val() || !$("input[name='nacinPlacanja']:checked").val()) {
            $(".error-box").text("Obavezan izbor naèina plaæanja i prevoza pre potvrde kupovine!");
            $("#dialog-message").dialog("open");
        } else {
            $(".error-box").text("Potvrda korpe u toku. Molimo saèekajte!");
            $("#dialog-message-i").dialog("open");
            $("#chceckOutBasketBtn").attr('disabled', 'disabled');
            $("#chceckOutBasket").submit();
        }
    });
    /*   iskomentarisano kako bi se omogu?ila kombinacija CAS i Brza Po?ta
     $("input[name='nacinPlacanja'],input[name='nacinTransporta']").live('change', function() {
     if ( $(this).val() == 'CAS') {
     $("input[name='nacinTransporta'][value='5']").attr("checked", false);
     $("input[name='nacinTransporta'][value='5']").attr('disabled', 'disabled');
     }
     else if ($(this).val() == '5') {
     $('input[value="CAS"]').attr("checked", false);
     $('input[value="CAS"]').attr('disabled', 'disabled');
     }
     else {
     $("input[name='nacinPlacanja'][value='CAS']").removeAttr( "disabled" );
     $("input[name='nacinTransporta'][value='5']").removeAttr( "disabled" );
     }
     });
     **/


    var currentUrl = window.location.href;
    if (currentUrl.indexOf("download?tipDownload=") > 0) $("html, body").animate({scrollTop: $("#download").offset().top}, 'slow');

});




