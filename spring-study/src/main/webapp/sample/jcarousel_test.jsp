<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/jcarousel/examples/_shared/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/jcarousel/examples/basic/jcarousel.basic.css"/>
<style type="text/css">
/** Carousel Pagination **/

.jcarousel-pagination {
    position: absolute;
    bottom: 20px;
    left: 45%;
    margin: 0;
    margin-left: -22px;
}

@media (max-width: 620px) {
    .jcarousel-pagination {
        bottom: -35px;
    }
}

.jcarousel-pagination a {
    text-decoration: none;
    display: inline-block;

    font-size: 11px;
    height: 7px;
    width: 3px;
    min-width: 5px;
    line-height: 7px;

    background: #fff;
    color: #4E443C;
    border-radius: 10px;
    text-indent: -9999px;

    margin-right: 7px;

    -webkit-box-shadow: 0 0 2px #4E443C;
    -moz-box-shadow: 0 0 2px #4E443C;
    box-shadow: 0 0 2px #4E443C;
}

.jcarousel-pagination a.active {
    background: #4E443C;
    color: #fff;
    opacity: 1;

    -webkit-box-shadow: 0 0 2px #F0EFE7;
    -moz-box-shadow: 0 0 2px #F0EFE7;
    box-shadow: 0 0 2px #F0EFE7;
}
</style>
<script type="text/javascript" src="<%=contextPath%>/common/js/jcarousel/libs/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/jcarousel/dist/jquery.jcarousel.min.js"></script>
<script type="text/javascript">
(function($) {
    $(function() {
        $('.jcarousel').jcarousel();

        $('.jcarousel-control-prev')
            .on('jcarouselcontrol:active', function() {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function() {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '-=1'
            });

        $('.jcarousel-control-next')
            .on('jcarouselcontrol:active', function() {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function() {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '+=1'
            });

        $('.jcarousel-pagination')
            .on('jcarouselpagination:active', 'a', function() {
                $(this).addClass('active');
            })
            .on('jcarouselpagination:inactive', 'a', function() {
                $(this).removeClass('active');
            })
            .jcarouselPagination();
        
        /*
        $('.jcarousel-pagination')
	        .on('jcarouselpagination:active', 'a', function() {
	            $(this).addClass('active');
	        })
	        .on('jcarouselpagination:inactive', 'a', function() {
	            $(this).removeClass('active');
	        })
	        .on('click', function(e) {
	            e.preventDefault();
	        })
	        .jcarouselPagination({
	            item: function(page) {
	                return '<a href="#' + page + '">' + page + '</a>';
	            }
	        });
         */
        
    });
})(jQuery);

</script>
</head>
<body>
        <div class="wrapper">
            <h1>Basic carousel</h1>

            <p>This example shows how to setup a basic carousel with prev/next controls and pagination.</p>

            <div class="jcarousel-wrapper">
                <div class="jcarousel">
                    <ul>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img1.jpg" width="600" height="400" alt=""></li>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img2.jpg" width="600" height="400" alt=""></li>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img3.jpg" width="600" height="400" alt=""></li>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img4.jpg" width="600" height="400" alt=""></li>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img5.jpg" width="600" height="400" alt=""></li>
                        <li><img src="<%=contextPath%>/common/js/jcarousel/examples/_shared/img/img6.jpg" width="600" height="400" alt=""></li>
                    </ul>
                </div>

                <p class="photo-credits">
                    Photos by <a href="http://www.mw-fotografie.de">Marc Wiegelmann</a>
                </p>

                <a href="#" class="jcarousel-control-prev">&lsaquo;</a>
                <a href="#" class="jcarousel-control-next">&rsaquo;</a>
                
                <p class="jcarousel-pagination">
                    
                </p>
            </div>
        </div>
</body>
</html>