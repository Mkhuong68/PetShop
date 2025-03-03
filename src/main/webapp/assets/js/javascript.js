//cuộn header 
$(window).scroll(function () {
    let header = $('header').height();
    if ($(window).scrollTop() > 20) {
        $('header').addClass('fixed');
    } else {
        $('header').removeClass('fixed');
    }
});

$('.res-menu').click(function () {
    $(this).toggleClass('active');
    if ($('.menu').attr('data-show') == 0) {
        $('.menu').addClass('active');
        $('.menu').removeClass('noactive');
        $('.menu').attr('data-show', 1);
    } else {
        $('.menu').addClass('noactive');
        setTimeout(function () {
            $('.menu').removeClass('active');
            $('.menu').attr('data-show', 0);
        }, 200)
    }
});

$(document).ready(function () {
    $('[data-toggle=search-form]').click(function () {
        $('.search-form-wrapper').toggleClass('active');
        $('.search-form-wrapper .search').focus();
        $('.wrap').toggleClass('search-form-open sidebar-move');
    });
    $('[data-toggle=search-form-close]').click(function () {
        $('.search-form-wrapper').removeClass('active');
        $('.wrap').removeClass('search-form-open');
    });
    $('.search-form-wrapper .search').keypress(function (event) {
        if ($(this).val() == "Search") $(this).val("");
    });

    $('.search-close').click(function (event) {
        $('.search-form-wrapper').removeClass('active');
        $('.wrap').removeClass('search-form-open');
    });
});
window.onload = function () {
    var searchnav = document.getElementById('search-nav');
    var maincontent = document.getElementById('main-content');
    var overlay = document.getElementById('site-overlay');
    var openMenu = document.getElementById('open-search');
    document.onclick = function (e) {
        if (e.target.id == 'main-content') {
            searchnav.classList.remove('active');
            maincontent.classList.remove('sidebar-move');
            overlay.classList.remove('active');
        }
        if (e.target === openMenu) {
            searchnav.style.display = 'block';
            maincontent.classList.add('search-form-open sidebar-move');
            overlay.classList.add('active');
        }
    };
};
$('.banner-prod').slick({
    dots: true,
    arrow: true,
    infinite: true,
    slidesToScroll: 1,
    slidesToShow: 1
});
$('.slide-prd').slick({
    arrow: true,
    infinite: true,
    slidesToScroll: 1,
    slidesToShow: 4,
    responsive: [{
            breakpoint: 1199,
            settings: { slidesToShow: 3, slidesToScroll: 1 }
        },
        {
            breakpoint: 991,
            settings: { slidesToShow: 2, slidesToScroll: 1 }
        },
        {
            breakpoint: 575,
            settings: { slidesToShow: 1, slidesToScroll: 1 }
        }
    ]
});

$('.slide-partner').slick({
    dots: false,
    arrow: false,
    infinite: false,
    slidesToShow: 6,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    responsive: [{
            breakpoint: 1199,
            settings: {
                slidesToShow: 5,
                slidesToScroll: 1
            }
        },
        {
            breakpoint: 991,
            settings: {
                slidesToShow: 4,
                slidesToScroll: 1
            }
        },
        {
            breakpoint: 767,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 1
            }
        },
        {
            breakpoint: 575,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 1
            }
        }
    ]
});


document.addEventListener("DOMContentLoaded", function() {
    const addProductBtn   = document.getElementById("add-product");
    const productNameInp  = document.getElementById("product-name");
    const productDescInp  = document.getElementById("product-desc");
    const productPriceInp = document.getElementById("product-price");
    const productImageInp = document.getElementById("product-image");
    const productList     = document.getElementById("product-list");
  
    addProductBtn.addEventListener("click", function() {
      const nameValue  = productNameInp.value.trim();
      const descValue  = productDescInp.value.trim();
      const priceValue = productPriceInp.value.trim();
      const imageValue = productImageInp.value.trim();
  
      if (!nameValue || !descValue || !priceValue) {
        alert("Vui lòng điền đủ Tên, Mô tả và Giá sản phẩm!");
        return;
      }
  
      const productItem = document.createElement("div");
      productItem.classList.add("product-item");
  
      if (imageValue) {
        const productImg = document.createElement("img");
        productImg.src = imageValue;
        productImg.alt = nameValue;
        productItem.appendChild(productImg);
      }
  
      const productTitle = document.createElement("h4");
      productTitle.textContent = nameValue;
      productItem.appendChild(productTitle);
  
      const productDesc = document.createElement("p");
      productDesc.textContent = descValue;
      productItem.appendChild(productDesc);
  
      const productPrice = document.createElement("span");
      productPrice.classList.add("price");
      productPrice.textContent = priceValue + " VND";
      productItem.appendChild(productPrice);
  
      productList.appendChild(productItem);
  
      // Reset input
      productNameInp.value  = "";
      productDescInp.value  = "";
      productPriceInp.value = "";
      productImageInp.value = "";
    });
  });

  //product detail 

  // 1. Đổi ảnh chính khi click vào thumbnail
const thumbnails = document.querySelectorAll(".thumbnail");
const mainImage = document.getElementById("current-image");

thumbnails.forEach(thumb => {
  thumb.addEventListener("click", () => {
    // Bỏ active cũ
    thumbnails.forEach(t => t.classList.remove("active"));
    // Thêm active cho thumbnail được click
    thumb.classList.add("active");

    // Lấy link ảnh gốc từ data-full
    const fullImageUrl = thumb.getAttribute("data-full");
    // Đổi ảnh chính
    mainImage.src = fullImageUrl;
  });
});

// 2. Xử lý Tabs (Mô tả, Đánh giá, Hỏi đáp)
const tabButtons = document.querySelectorAll(".tab");
const tabContents = document.querySelectorAll(".tab-content");

tabButtons.forEach(button => {
  button.addEventListener("click", () => {
    // Xóa active tất cả tab
    tabButtons.forEach(btn => btn.classList.remove("active"));
    // Ẩn tất cả nội dung
    tabContents.forEach(content => content.classList.remove("active"));

    // Thêm active cho tab được click
    button.classList.add("active");
    // Lấy id tab cần hiển thị
    const tabId = button.getAttribute("data-tab");
    const targetContent = document.getElementById(tabId);
    // Hiển thị tab-content tương ứng
    targetContent.classList.add("active");
  });
});

// 3. Xử lý nút “Thêm vào giỏ” & “Mua ngay”
document.getElementById("add-to-cart").addEventListener("click", () => {
  alert("Sản phẩm đã được thêm vào giỏ hàng!");
});

document.getElementById("buy-now").addEventListener("click", () => {
  alert("Chuyển đến trang thanh toán...");
});
