# FrontEnd Project

## App Directories

```
src
├── assets
│   ├── content
│   │   └── dam
│   ├── css
│   ├── fonts
│   │   ├── bootstrap
│   │   ├── iconfont
│   │   ├── ionicons
│   │   └── webfonts
│   ├── images
│   │   └── icons
│   ├── js
│   │   ├── data
│   │   ├── libs
│   │   └── partials
│   └── scss
│       └── components
│           └── content
├── data
├── helpers
└── views
    ├── layouts
    ├── pages
    └── partials
        ├── component1
        ├── component2
        ├── component3
        └── global
```

## How to build and run app

Used `gulp-sass` instead `ruby compass`

```shell
# $: gem install bootstrap-sass
$: npm install --save-dev gulp-imagemin
$: npm install
$: gulp build
```

go to `localhost:3000/your_html_page.html` (i.e. checkOut.html)

Build files can be found under ./build directory.

### How to delete clean previous

$: gulp clean

### Note

*Do not npm with sudo under team collaboration environment.*

### Ba Structure for elements

Apply below elements

For `<table>`

```html
<table
width="100%"
height="100%"
border="0"
cellpadding="0"
cellspacing="0"
align="center"
style="
        border-spacing:0;
        border-collapse:collapse;
        mso-table-lspace:0;
        mso-table-rspace:0;
        height: 100%; 
        width: 100%; 
        margin: 0; 
        padding: 0;" >
</table>
```

#### tr elements does not has styles or properties

For `<td>`

```html
<td
    align="left"
    colspan="3"
    valign="top"
    style="
      border-collapse:collapse;
      mso-table-lspace:0;
      mso-table-rspace:0;
      background-color: var(--foo);
      color: var(--foo);
      height: N px;
      width: 100%;
      padding: N;
      vertical-align: top;
      font-size: N;
      font-weight: normal;
      line-height: N;
      mso-line-height-rule: exactly;
    ">
</td>
```

For `<img>`

```html
<img
        src="../assets/images/fios-logo.svg"
        width="163"
        height="65"
        alt="FIOS Logo"
        title=""
        border="0"  
        style="
          display: block;
          border-width: 0;
          -ms-interpolation-mode:bicubic;
">
```

For `<a>`

```html
<a
href="https://www.verizon.com/home/myverizon/?LOBCode=C&PromoTCode=MZV07&PromoSrcCode=V&POEId=VU1SP" 
style="
        font-size: 32px;
        font-weight: normal;
        line-height: 36px;
        mso-line-height-rule: exactly;
        color:         var(--brand-header-navigation-text-color);
        text-decoration:none;
">
        My Verizon
</a>
```