function buildNavbar() {
    var dataFile = "js/DetailsData.json";
    loadJson(dataFile);
}
function loadJson(jsonFile) {
    $.getJSON(jsonFile, function (json) {
    loadNavBar(json);
    loadBanner(json);
    loadFooter(json);
    });
}

function loadBanner(data){
    var banner = $("#banner");
    $("title").append(data.subject + " " + data.number);
    var text = data.subject + " " + data.number + " - " + data.semester + " " + data.year
                + "<br>" + data.title; 
    banner.append(text);
}

function loadNavBar(data){
    var navbar = $('#navbar');
    var bannerImage = "<a href=\"http://www.stonybrook.edu/\"><img alt=\"Stony Brook University\" class=\"sbu_navbar\" src=\"images/banner_image.png\"></a>";
    
    var nav = "";

    var documentName = document.location.pathname.match(/[^\/]+$/)[0];

    for (var i = 0; i < data.details.length; i++) {
        var detail = data.details[i];
        var link = "<a class=\"";
        if (detail.file_name === documentName)
            link += "open_nav\" ";
        else
            link += "nav\" ";

        link += "href=\"" + detail.file_name + "\" " + "id=\"";

        var id = detail.navbar_title.toLowerCase() + "_link";
        link += id +"\">" + detail.navbar_title + "</a>";

        nav += link;
    }

    navbar.append(bannerImage);
    navbar.append(nav);
}

function loadFooter(data) {
    var footer = $("#footer");
    var leftImage = "<a href=\"http://www.stonybrook.edu/\">" 
        +"<img alt=\"SBU\" class=\"sunysb\" src=\"images/left_image.png\" style=\"float:left\"></a>";
    var rightImage = "<a href=\"http://www.stonybrook.edu/\">" 
        +"<img alt=\"CS\" src=\"images/right_image.png\" style=\"float:right\"></a>";
    var instructor = "<p style=\"font-size:9pt; text-align:center;\">Web page created and maintained<br>"
                    + "by <span id=\"instructor_link\"><a href=\"" + data.instructor_home + "\">" + data.instructor_name + "</a>";
    footer.append(leftImage);
    footer.append(rightImage);
    footer.append(instructor);
}