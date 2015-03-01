load "$NCARG_ROOT/lib/ncarg/nclscripts/csm/gsn_code.ncl" ;gsn一般界面;NCL函数、过程以及共享对象
load "$NCARG_ROOT/lib/ncarg/nclscripts/csm/gsn_csm.ncl" ;gsn_csm 界面;NCL函数、过程以及共享对象
load "$NCARG_ROOT/lib/ncarg/nclscripts/csm/contributed.ncl" ;差值算法load 
;load "$NCARG_ROOT/lib/ncarg/nclscripts/csm/shea_util.ncl"
;*****************************************************
; 读取配置文件 ./color.ini
;  %Y-%m-%d %H:%M:%S
;  %y    年份最后两位数位 (00-99)
;  %Y    年份 2013
;  %m    月份(01-12)
;  %d    按月计的日期(例如：01)
;  %H    小时(00-23)
;  %I    小时(00-12)
;  %M    分(00-59)
;  %S    秒(00-60)
;  %F    完整日期格式，等价于 %Y-%m-%d

;*****************************************************
undef ("getSystemDate")
function getSystemDate(fmt)
begin
    date = systemfunc("date '+" + fmt + "'")
    return date
end
;*****************************************************
; 获取时间差计算后的系统时间
;  %Y-%m-%d %H:%M:%S
;  %y    年份最后两位数位 (00-99)
;  %Y    年份 2013
;  %m    月份(01-12)
;  %d    按月计的日期(例如：01)
;  %H    小时(00-23)
;  %I    小时(00-12)
;  %M    分(00-59)
;  %S    秒(00-60)
;  %F    完整日期格式，等价于 %Y-%m-%d
; diff 时间差，必须为整数
; type  英文 month day hour miniute second
;*****************************************************
undef ("getDate")
function getDate(fmt, diff, type)
begin
    date = systemfunc("date '+" + fmt + "'  --date='" + diff + " " +type)
    return date
end
;*****************************************************
; 根据字符串的时间计算某个时间差的时间
;  %Y-%m-%d %H:%M:%S
;  %y    年份最后两位数位 (00-99)
;  %Y    年份 2013
;  %m    月份(01-12)
;  %d    按月计的日期(例如：01)
;  %H    小时(00-23)
;  %I    小时(00-12)
;  %M    分(00-59)
;  %S    秒(00-60)
;  %F    完整日期格式，等价于 %Y-%m-%d
; diff 时间差，必须为整数
; type  英文 day hour miniute second
;*****************************************************
undef("getDiffDate")
function getDiffDate(dateStr, fmtout, diff, diffType )
begin
    str = "date -d '" + dateStr +"' +%s"
    tm = stringtointeger(systemfunc(str))
    
    if (str_lower(diffType) .eq. "day") then
        diff = diff * 24 * 60 * 60
    end if
    
    if (str_lower(diffType) .eq. "hour") then
        diff = diff * 60 * 60
    end if
    
    if (str_lower(diffType) .eq. "miniute") then
        diff = diff * 60
    end if
    tm = tm + diff
    tm2 = systemfunc("expr " + tm)
;    cmd = "date -d @" + tm2 + "  +%Y%m%d"
    return systemfunc("date -d @" + tm2 + "  '+" + fmtout + "'")
end
;*****************************************************
; 打印

;*****************************************************
undef ("myPrint")
procedure myPrint(str)
begin
    system("echo [" + getSystemDate("%Y-%m-%d %H:%M:%S") + "]  " + str)
end

;*****************************************************
; 休眠n秒
;*****************************************************
undef ("sleep")
function sleep(n)
begin
    system("sleep " + n + "s")
    return 0
end
;*****************************************************
; 读取配置文件 ./color.ini
;*****************************************************
undef ("getColorCfg")
function getColorCfg(colorFi)
begin
    ;colorFi = "./color.ini"
    argu = asciiread(colorFi,-1,"string")
    return argu
end
;*****************************************************
; 读取配置文件中的颜色表
;*****************************************************
undef ("getColorMap")
function getColorMap(colorFi)
begin
    ; config file
    argu = getColorCfg(colorFi)
    ; 配置的颜色条目
    i_colorNum = dimsizes(argu) - 6
    af_colorMap = new ((/i_colorNum, 3/), "float", 255)
    
    do i=0, i_colorNum-1, 1
        af_colorTmp = stringtofloat(str_split(argu(i+6), ","))
        af_colorMap(i, 0) = af_colorTmp(0) / 255
        af_colorMap(i, 1) = af_colorTmp(1) / 255
        af_colorMap(i, 2) = af_colorTmp(2) / 255
    end do
    return af_colorMap
end
;*****************************************************
; 读取配置文件中的颜色顺序
;*****************************************************
undef ("getColorOrder")
function getColorOrder(colorFi)
begin
    argu = getColorCfg(colorFi)
    ;颜色顺序
    str_colorOrder = argu(2)
    ai_colorOrder = stringtointeger(str_split(str_colorOrder, ",")) + 5
    return ai_colorOrder
end
;*****************************************************
; 读取配置文件中的数值定义
;*****************************************************
undef ("getColorValueDef")
function getColorValueDef(colorFi)
begin
    argu = getColorCfg(colorFi)
    ;数值意义
    str_colorValueDef = argu(4)
    af_colorValueDef = stringtofloat(str_split(str_colorValueDef, ","))
    return af_colorValueDef
end
;*****************************************************
; 读取配置文件中的数值定义_字符串
;*****************************************************
undef ("getColorValueDefStr")
function getColorValueDefStr(colorFi)
begin
    argu = getColorCfg(colorFi)
    ;数值意义
    str_colorValueDef = argu(4)
    strArr_colorValueDef = str_split(str_colorValueDef, ",")
    return strArr_colorValueDef
end
;*****************************************************
; 读取命令行参数返回字符串数组
; args 配置文件字符串，如下所示
; eg BUSIID=B1001,BUSINAME=TEST,SCRIPT=TEST.ncl,CONFIG=test.config
; paraName 属性名称
;*****************************************************
undef ("getArgsPara")
function getArgsPara(args, paraName)
begin
    arrs = str_split(args, ",")
    do i=0, dimsizes(arrs)-1, 1
        if (0 .eq. dimsizes(arrs(i))) then
            break
        end if
        tmparr = str_split(arrs(i), "=")
        if (paraName .eq. tmparr(0)) then
            return tmparr(1)
        end if 
    end do
    return "null"
end
;*****************************************************
; 判断一行字符串是否是注释  “#”
; yes = TRUE  no = FALSE
;*****************************************************
undef ("isComment")
function isComment(str)
begin
    b = stringtochar(str)
    if ("#" .eq. b(0)) then
        return "TRUE"
    end if
    return "FALSE"
end
;*****************************************************
; 解析system.ini
; 返回该busiid下的配置  
; BUSIID=B1001,BUSINM=TEST,SCRIPT=TEST.ncl,CONFIG=test.config
;*****************************************************
undef ("readBusiInfoFromSystemINI")
function readBusiInfoFromSystemINI(busiID)
begin
    iniPath = "$NCLJOB_ROOT/config/system.ini"
    argu = asciiread(iniPath,-1,"string")
    do i=0, dimsizes(argu)-1, 1
        if (0 .eq. strlen(argu(i))) then
            continue
        end if
        if ("TRUE" .eq. isComment(argu(i))) then
            continue
        end if
        ; 行信息
        lineargu = str_split(argu(i), " ")
        
        if ("[BUSIID]" .eq. lineargu(0)) then
            rtnStr = ""
            rtnStr = "BUSIID="+lineargu(1)+","
        end if
        if ("[BUSINM]" .eq. lineargu(0)) then
            rtnStr = rtnStr + "BUSINM="+lineargu(1)+","
        end if
        if ("[SCRIPT]" .eq. lineargu(0)) then
            rtnStr = rtnStr + "SCRIPT=$NCLJOB_ROOT/business/"+lineargu(1)+","
        end if
        if ("[CONFIG]" .eq. lineargu(0)) then
            rtnStr = rtnStr + "CONFIG=$NCLJOB_ROOT/config/"+lineargu(1)
            tmpBusiId = getArgsPara(rtnStr, "BUSIID")
            if (busiID .eq. tmpBusiId ) then
                return rtnStr
            end if
        end if
    end do
    return "null"
end
;*****************************************************
; 截取字符串
; 返回字符串 
; str  原字符串, 
; startIdx 开始位置,
; endIdx   结束位置
; BUSIID=B1001,BUSINM=TEST,SCRIPT=TEST.ncl,CONFIG=test.config
;*****************************************************
undef("substr")
function substr(str, startIdx, endIdx)
begin
    b = stringtochar(str)
    c = charactertostring(b(startIdx:endIdx))
    return c
end
;*****************************************************
; 解析业务的配置
; 返回该busiid下paramId的配置  
; COLORCFG=color_jszw.ini
;*****************************************************
undef ("readBusiINI")
function readBusiINI(iniPath, paraIDInput)
begin
    argu = asciiread(iniPath,-1,"string")
    do i=0, dimsizes(argu)-1, 1
        if (0 .eq. strlen(argu(i))) then
            continue
        end if
        if ("TRUE" .eq. isComment(argu(i))) then
            continue
        end if
        
        ; 行信息
        lineargu = str_split(argu(i), " ")
        paraId = substr(lineargu(0),1, strlen(lineargu(0))-2)
        if (paraIDInput .eq. paraId)
            return lineargu(1)
        end if
    end do
    return "null"
end

;##########################################################################################
;  procedure
;##########################################################################################
;*****************************************************
; 反转二维数组
; data 待转换数据,
; latr 纬度是否转换  "False" 不转  "True" 转, 
; lonr 经度是否转换  "False" 不转  "True" 转, 
; 
;*****************************************************
undef ("reverse2DArr")
procedure reverse2DArr(data, rtndata, latr, lonr)
begin
    ; lat 反转 lon 不转
    if ("TRUE" .eq. str_upper(latr) .and. "FALSE" .eq. str_upper(lonr)  )
        rtndata = data(::-1, :)
    end if 
    if ("FALSE" .eq. str_upper(latr) .and. "TRUE" .eq. str_upper(lonr)  ) 
        rtndata = data(:, ::-1)
    end if 
    if ("TRUE" .eq. str_upper(latr) .and. "TRUE" .eq. str_upper(lonr)  )
        tmpdata1 = data(::-1, :)
        tmpdata2 = tmpdata1(:, ::-1)
        rtndata = tmpdata2
    end if 
    if  ("FALSE" .eq. str_upper(latr) .and. "FALSE" .eq. str_upper(lonr)  )
        rtndata = data
    end if
end
;***********************************************************
; 确定ViewPort的大小 在viewPort四周画框
;***********************************************************
undef ("draw_vp_box")
procedure draw_vp_box(wks, plot)
local vpx, vpy, vpw, vph, xbox, ybox, lnres, mkres, txres
begin
    getvalues plot
        "vpXF"      : vpx
        "vpYF"      : vpy
        "vpWidthF"  : vpw
        "vpHeightF" : vph
    end getvalues
    setvalues plot
        "vpXF"      : 0
        "vpYF"      : 1
        "vpWidthF"  : 1
        "vpHeightF" : 1
    end setvalues
    
    xbox = (/vpx, vpx+vpw, vpx+vpw, vpx, vpx/)
    ybox = (/vpy, vpy, vpy-vph, vpy-vph, vpy/)
    lnres = True
    lnres@gsLineColor = "Blue"
    lnres@gsLineThicknessF  = 1
    gsn_polyline_ndc(wks, xbox, ybox, lnres)
end
;***********************************************************
; 确定目录是否存在，不存在则创建
;***********************************************************
undef ("checkOrCreateDir")
procedure checkOrCreateDir(dir)
begin
    if(isfilepresent(dir)) then
        ;print(dir+ " is already exists!")
    else
        system("mkdir -p " + dir)
    end if
end
;***********************************************************
; 确定NDC的大小  在NDC四周画框
;***********************************************************
undef ("draw_bb_box")
procedure draw_bb_box(wks, plot)
local bb, top, bot, lft, rgt, xbox, ybox, lnres
begin
    bb = NhlGetBB(plot)
    top = bb(0);1
    bot = bb(1);0
    lft = bb(2);0
    rgt = bb(3);1
    ;print(bb)
    xbox = (/rgt, lft, lft, rgt, rgt/)
    ybox = (/top, top, bot, bot, top/)
    
    lnres = True
    lnres@gsLineColor = "White"
    lnres@gsLineThicknessF  = 5
    gsn_polyline_ndc(wks, xbox, ybox, lnres)
end
;*************************************************************
;***********************************************************
; 在图上的某个位置画色标
; wks           工作台, 
; contour       画图空间 gsn_cum_contour返回结果, 
; fillColors    填充颜色 colorMap的色标数组, 
; vpWidthF      色标矩形宽  contour的vpw的百分比, 
; vpHeightF     色标矩形高  contour的vpw的百分比, 
; lbLabelFontHeightF  色标标签字符串长度默认0.02, 
; labelStrings  色标标签字符串数组, 
; posX          色标矩形左上角坐标x,   可以为负数
; poxY          色标矩形左上角坐标y    可以为负数
;***********************************************************
undef ("draw_label_box")
procedure draw_label_box(wks, contour, fillColors, vpWidthF, vpHeightF, lbLabelFontHeightF, labelStrings, posX, posY)
local lbres, labelid, amres, annoid
begin
    lbres = True
    lbres@lbAutoManage = False
    lbres@lbFillColors = fillColors
    lbres@lbPerimOn = False
    lbres@lbMonoFillPattern = True
    ; 图标垂直  Vertical
    lbres@lbOrientation    = "Vertical"
    ; 左上角坐标
    ;lbres@vpXF                  = 0.8     
    ;lbres@vpYF                  = 0.2     
    ; 调节矩形的长宽
    lbres@vpWidthF = vpWidthF
    lbres@vpHeightF = vpHeightF
    ; 图标字体大小 默认0.02
    lbres@lbLabelFontHeightF = lbLabelFontHeightF;0.015
    ; 关闭图标标题
    lbres@lbTitleOn = False 
    ;labelStrings = (/"65","60","55","50","45","40","35","30","25","20","15","10","5","0","-5"/)
    ;gsn_labelbar_ndc(wks, 16, labelStrings, 0.2,0.7,lbres)
    labelid = gsn_create_labelbar(wks, dimsizes(fillColors)-1, labelStrings, lbres)
    
    amres = True
    amres@amParallelPosF   = posX; 0.4            ; Center labelbar.
    amres@amOrthogonalPosF = posY; -0.1           ; Move down, away from plot

    annoid = gsn_add_annotation(contour,labelid,amres)
end
;***********************************************************
; 在图上的某个位置写文本
; wks           工作台, 
; contour       画图空间 gsn_cum_contour返回结果, 
; vpWidthF      文本矩形宽  contour的vpw的百分比, 
; vpHeightF     文本矩形高  contour的vpw的百分比, 
; lbLabelFontHeightF  文本标签字符串长度默认0.02, 
; labelStrings  文本标签字符串数组, 
; posX          文本矩形左上角坐标x,   可以为负数
; poxY          文本矩形左上角坐标y    可以为负数
;***********************************************************
undef ("draw_text_box")
procedure draw_text_box(wks, contour, vpWidthF, vpHeightF, lbLabelFontHeightF, labelStrings, posX, posY)
local txres, labelid, amres, annoid
begin
    txres = True
    ; 图标垂直  Vertical
    txres@lbOrientation    = "Vertical"
    ; 调节矩形的长宽
    txres@vpWidthF = vpWidthF
    txres@vpHeightF = vpHeightF
    ; 图标字体大小 默认0.02
    txres@txFontHeightF  = 0.02;0.05
    txres@txFontColor    = "black"
    labelid = gsn_create_text(wks, labelStrings, txres)
    
    amres = True
    amres@amParallelPosF   = posX; 0.4            ; Center labelbar.
    amres@amOrthogonalPosF = posY; -0.1           ; Move down, away from plot

    annoid = gsn_add_annotation(contour,labelid,amres)
end
;*************************************************************
undef ("setAllBusiCommonRes")
procedure setAllBusiCommonRes(cnres)
begin
    cnres@gsnFrame                  = False
    cnres@gsnDraw                   = False
    ; 最大化 默认为False，如果是True的话，NDC的XY会自动缩小0.02
    cnres@gsnMaximize               = False

    ; 图片右下角的说明文字“CONTOUR FROM 8 TO 56 BY 4”
    ;cnres@cnInfoLabelOn            = True
     ;去掉四周边框
    cnres@tmXBBorderOn              = False
    cnres@tmXTBorderOn              = False
    cnres@tmYLBorderOn              = False
    cnres@tmYRBorderOn              = False
     ;去掉刻度
    cnres@tmXBOn                    = False
    cnres@tmXTOn                    = False
    cnres@tmYLOn                    = False
    cnres@tmYROn                    = False
    ; 左上角坐标
    cnres@vpXF                      = 0     
    cnres@vpYF                      = 1     
    ; 调节矩形的长宽
    cnres@vpWidthF                  = 1.
    cnres@vpHeightF                 = 1.
end
;*************************************************************
undef ("setCommonRes")
procedure setCommonRes(cnres)
begin
    setAllBusiCommonRes(cnres)
    
    ; 无数据时不显示任何文字
    cnres@cnNoDataLabelOn           = False
    cnres@cnConstFLabelOn           = False
    cnres@cnLevelSelectionMode      = "ExplicitLevels"
    ; 图例
    cnres@lgLabelsOn                = False
    ; 使用颜色map的默认行为 默认true
    ;cnres@cnSpreadColors            = False
    
end
;*************************************************************
; 设置风羽常用参数
undef ("setCommonUVRes")
procedure setCommonUVRes(uvres)
begin
    uvres@gsnFrame                  = False
    uvres@gsnDraw                   = False
    uvres@gsnFrame                  = False
    uvres@vcGlyphStyle              = "WindBarb"
    ; 无数据时不显示文字
    uvres@vcNoDataLabelOn           = False
    ; 线宽
    uvres@vcWindBarbLineThicknessF  = 2.0;风杆粗细
    ; 羽毛长度
    uvres@vcWindBarbTickLengthF     = 0.5
    ; 每一根风羽箭头上的羽毛间距
;    uvres@vcWindBarbTickSpacingF    = 0.2
    uvres@vcWindBarbTickSpacingF    = 0.15
    ; 圆形图大小 -1为黑点
    uvres@vcWindBarbCalmCircleSizeF = -1
    ; 风系数  国际标准和国内标准的转换 默认1
  	uvres@vcWindBarbScaleFactorF = 2.5 
    ;uvres@vcLineArrowColor = "black" ; vector color
    ; 间距
    uvres@vcMinDistanceF = 0.02
    ;矢量大小
    uvres@vcRefMagnitudeF = 200
    ;矢量箭头长度
    uvres@vcRefLengthF = 0.015;风羽的整体大小
    uvres@vcRefAnnoOn               =False   ;去除reference vector 的注释
end
;*************************************************************
; 设置地图常用参数
undef ("setCommonMapRes")
procedure setCommonMapRes(cnres)
begin
    cnres@gsnAddCyclic  = False      ;由于我们的数据不是循环地球一周的，因此必须把这个置否

    cnres@mpDataSetName         = "Earth..4"   ; This new database contains
                                               ; divisions for other countries.
    cnres@mpDataBaseVersion     = "MediumRes"  ; Medium resolution database
    cnres@mpOutlineOn           = True         ; Turn on map outlines
    cnres@mpOutlineSpecifiers   = (/"Jiangsu"/)       ;China:states
    
    cnres@mpMinLatF             =  17;30;17          ; Asia limits
    cnres@mpMaxLatF             =  55;40;55
    cnres@mpMinLonF             =  72;100;72
    cnres@mpMaxLonF             = 136;120;136
    ; 加粗边界
;    cnres@mpOutlineBoundarySets  = "AllBoundaries"
    cnres@mpGeophysicalLineThicknessF= 2.      ; double the thickness of geophysical boundaries
    ;cnres@mpNationalLineThicknessF= 2.         ; double the thickness of national boundaries
    ;cnres@mpLimbLineThicknessF  = 2.
    cnres@mpUSStateLineThicknessF  = 2.         ; 加粗省界
    
;    cnres@mpProjection = "LambertConformal"   ;兰伯特投影
;    cnres@mpLambertMeridianF = 110.0
;    cnres@mpLimitMode = "LatLon"
;    cnres@mpLambertParallel1F = 0.001      ;Default: .001 ;可以自己改一改，看看投影有什么不同，挺有趣的
;    cnres@mpLambertParallel2F = 89.999    ;Default: 89.999
    
;    cnres@mpAreaMaskingOn = True   ;使能填充覆盖
;    cnres@mpMaskAreaSpecifiers = (/"China:states","Taiwan"/)   ;China:states
;    cnres@mpOceanFillColor = 0     ;用白色填充海洋  0是colormap的索引值
;    cnres@mpInlandWaterFillColor = 0  ;用白色填充内陆湖水 
end
;*************************************************************
; 将底色设置为透明
; 入口 ：
;      color   颜色   字符串
;                     white  
;      filename       文件名称
;*************************************************************
undef ("transparentBkColor")
procedure transparentBkColor(color, filename)
begin
    cmd = "convert -transparent " + color + " " + filename + " " + filename 
    system(cmd)
end
;*************************************************************
; 从文件正中合并两张图片
;*************************************************************
undef ("mergerImg")
procedure mergerImg(src, back, target)
begin
    cmd = "composite   -gravity center  " + src + " " + back + " " + target 
    system(cmd)
end

;*************************************************************
; pstopng  -rotate 90
;*************************************************************
undef ("psToThumbnailpng")
procedure psToThumbnailpng(src,  target, cmd1)
begin
    cmd = "convert -resize 320x320!   -bordercolor  white -transparent white -transparent black "+cmd1+" " + src + " "  + " " + target 
;    cmd = "convert -resize 320x320 -bordercolor  white -transparent white -transparent black "+cmd1+ src + " "  + " " + target 
    system(cmd)
    delete(cmd)
end
;*************************************************************
;计算k指数，tk表示温度，rh表示相对湿度，td表示露点（单位为C）（850hpa温度-500hpa温度）+850hpa露点-（700hpa温度-700hpa露点）  
undef ("calcuindexk")
function calcuindexk(lev,t,rh)          ;分别表示层数，温度（K)，相对湿度，起始终止经度，起始终止纬度
local k
begin
      nz8 = -1
      nz5 = -1
      nz7 = -1
      do i=0,dimsizes(lev)-1
         if (lev(i).eq.850) then
            nz8= i 
         end if
         if (lev(i).eq.700) then
            nz7= i 
         end if
         if (lev(i).eq.500) then
            nz5= i 
         end if
      end do
      if (nz8 .eq. -1 .or. nz7 .eq. -1 .or. nz5 .eq. -1) then
      		k = (/(/999.99, 999.99/)/)
          return k
      end if
      tk5 = t(nz5,:,:) 
      rh5  = rh(nz5,:,:) 
      
      tk8 = t(nz8,:,:) 
      rh8  = rh(nz8,:,:) 
      tdk8  = dewtemp_trh(tk8,rh8)               ;850hpa露点
       
      tk7 = t(nz7,:,:) 
      rh7  = rh(nz7,:,: ) 
      tdk7  = dewtemp_trh(tk7,rh7)              ;700hpa露点
        
      k = (tk8-tk5)+tdk8-273.15-(tk7-tdk7)          ;计算k指数，单位C
      return k
end
;**************************************************
; 保留最近6次使用的文件
;**************************************************
undef ("rmOldFiles")
procedure rmOldFiles(src) 
begin
    cmd = "cd " + src + " && ls -lut | awk '{if (NR>31) {print $NF}}' | xargs -n1 rm -f"
    system(cmd)
end
;**************************************************
; 删除临时文件
;**************************************************
undef ("rmTmpFiles")
procedure rmTmpFiles(srcFile) 
begin
    cmd = "rm -f " + srcFile
    system(cmd)
end
;**************************
; Get Micaps4 DataHead
; return array
;**************************
undef("getMicaps4Head")
function getMicaps4Head(mic4File)
begin
    head = readAsciiHead(mic4File, 3)
    contentparams = str_split(head(1), " ")
    return contentparams
end
;**************************
; Get Micaps4 DataHead 剖面图用
; return array
;**************************
undef("getMicaps4Head2")
function getMicaps4Head2(mic4File)
begin
    head = readAsciiHead(mic4File, 4)
    contentparams = str_split(head(2), " ")
    return contentparams
end
;**************************
; Get Micaps4 Data
;**************************
undef("getMicaps4Data")
function getMicaps4Data(mic4File, rows, cols)
begin
    odatas = readAsciiTable(mic4File, cols, "float", 3)
    datas  = onedtond(odatas, (/rows, cols/))
    datas@_FillValues = 0.0
    return datas
end
;**************************
; Get Micaps4 Data 剖面图用
;**************************
undef("getMicaps4Data2")
function getMicaps4Data2(mic4File, rows, cols)
begin
    odatas = readAsciiTable(mic4File, cols, "float", 4)
    datas  = onedtond(odatas, (/rows, cols/))
    datas@_FillValues = 0.0
    return datas
end
;**************************
; Get str_sort_by_substr Data 在某个字符串数组中，根据给定的子字符串位置排序
;**************************
undef("str_sort_by_substr")
function str_sort_by_substr (strs, startidx, endidx)
begin
    ; 字符串数组的个数
    strnums = dimsizes(strs)
    needSortStrs = new (strnums, "string")
    sortedStrs = new (strnums, "string")
    do i=0, strnums-1, 1
        tmpSortStr = substr(strs(i), startidx, endidx)
;        myPrint(tmpSortStr)
        needSortStrs(i) = tmpSortStr
    end do
    sqsort (needSortStrs)
    do i=0, strnums-1, 1
        do j=0, strnums-1, 1
            idx = str_index_of_substr(strs(j), needSortStrs(i), 0)
            if (ismissing(idx)) then
                continue
            else 
            sortedStrs(i) =  strs(j) 
            end if 
        end do    
    end do
    return sortedStrs
end
;****************************
; 获取文件大小
; 入口： 文件名
; 出口： 文件大小，文件不存在，返回-1
;***************************

undef("getFileSize")
function getFileSize(filePath)
begin
    cmd = "ls  -s  " + filePath
    rtn = systemfunc(cmd)
    if (ismissing(rtn)) then
        return -1
    end if
    size = str_split(rtn, " ")
    return stringtointeger(size(0))
end

;****************************
; 根据经纬度过滤数据
; 入口： 原始数据(lon、lat、value)
; 出口： 过滤后的数据(lon、lat、value)
;***************************
undef("fliterlatlon")
function fliterlatlon(data,validVale)
begin
		stationConfig = "$NCLJOB_ROOT/config/JSSTATION.txt"
		datasize = dimsizes(data)    
    nrows = 72
  	ncols = 3
  	latlonData  = asciiread(stationConfig,(/nrows,ncols/),"float")
    newdata = new ((/nrows*3/), "float", validVale)

    lon =  new ((/datasize/3/), "float", validVale)
    lat = new ((/datasize/3/), "float", validVale)
 
    latlonsize= dimsizes(latlonData)
    newdata = validVale*-1

   
    nlon=latlonData(:,1)
    nlat=latlonData(:,2)  
    do j=0,nrows-1,1 ;       
		    do i=0, datasize-1, 3
		        lon(i/3) = data(i)
		        lat(i/3) = data(i+1)	 
		        lonSub  =lon(i/3)-nlon(j)
		        latSub  =lat(i/3)-nlat(j); 
		        ;print(lonSub)
		        newdata(j*3)= nlon(j);
			      newdata(j*3+1)= nlat(j);
		        
		        if((.not. ismissing(latSub) ).and.(.not. ismissing(lonSub) ).and.(latSub .lt.  0.00001).and. (lonSub .lt. 0.00001 )) then  
		          valueDis = data(i+2) - newdata(j*3+2)
		        	if ((data(i+2) .lt. validVale).and.(.not. ismissing(valueDis) ) .and. (valueDis .gt. 0.)  ) then  		        		
			        		newdata(j*3+2)= data(i+2)
			        		continue
			        end if
 			       end if
		    end do		    	      
	   end do
	   delete(latlonData)
	   delete(lon)
	   delete(lat)
	   ;print (newdata)
    return newdata;
end
;****************************
; 根据经纬度过滤数据
; 入口： 原始数据(lon、lat、U、V)
; 出口： 过滤后的数据(lon、lat、U、V)
;***************************
undef("fliterlatlonuv")
function fliterlatlonuv(data,validVale)
begin
		stationConfig = "$NCLJOB_ROOT/config/JSSTATION.txt"
		datasize = dimsizes(data)    
    nrows = 72
  	ncols = 3
  	latlonData  = asciiread(stationConfig,(/nrows,ncols/),"float")
    newdata = new ((/nrows*4/), "float", validVale)

    lon =  new ((/datasize/4/), "float", validVale)
    lat = new ((/datasize/4/), "float", validVale)
 
    latlonsize= dimsizes(latlonData)
   
    nlon=latlonData(:,1)
    nlat=latlonData(:,2)  
    do j=0,nrows-1,1 ;       
		    do i=0, datasize-1, 4
		        lon(i/4) = data(i)
		        lat(i/4) = data(i+1)	 
		        ;print(lonSub)
		        newdata(j*4)= nlon(j);
			      newdata(j*4+1)= nlat(j);
			      lonSub  =lon(i/4)-nlon(j)
		        latSub  =lat(i/4)-nlat(j); 
		        if((.not. ismissing(latSub) ).and.(.not. ismissing(lonSub) ).and.(latSub .lt.  0.0001).and. (lonSub .lt. 0.0001 )) then   
		        	if ((data(i+2) .lt. validVale) .and. (data(i+3) .lt. validVale)) then  		        		
			        		newdata(j*4+2)= data(i+2);
			        		newdata(j*4+3)= data(i+3);
			        		continue
			        else		
			            newdata(j*4+2)= validVale;
			        		newdata(j*4+3)= validVale;
			        end if
			        
			       end if
		    end do		    	      
	   end do
	   delete(latlonData)
	   delete(lon)
	   delete(lat)
    return newdata;
end
;******************************************
;createDistrImg 抽取站号
;输入：一维的字符串数组，每个字符串存放站号经纬度要素等信息
;输出：同输入
;******************************************
undef("selectStation")
function selectStation(tmpData)
begin
    datasize = dimsizes(tmpData)
		;只取站号以5开头的
		station   = new (datasize, "string","999999.")
		lons      = new ((/datasize(0)/), "float", 999999.)
		lats      = new ((/datasize(0)/), "float", 999999.)
		pressDatas = new ((/datasize(0)/), "float", 999999.)
		
		j=0
		do i=0, datasize-1, 1  
		    strarr = str_split(tmpData(i)," ")
		    station(i)  =  strarr(0)
		    begins = substr(station(i),0,0)    
		    if(begins.eq."5") then
		        lons(j)       = stringtofloat(strarr(1))
		        lats(j)       = stringtofloat(strarr(2))
		        pressDatas(j) = stringtofloat(strarr(4))
		        j=j+1
		    end if
		    delete(strarr)
		end do
		
		newdata = new ((/j,3/), "float", 999999.)
		newdata(:,0)=lons(0:j-1)
		newdata(:,1)=lats(0:j-1)
		newdata(:,2)=pressDatas(0:j-1)
		do i=0, j-1, 1
		    newdata(i,0) = lons(i)
		    newdata(i,1) = lats(i)
		    newdata(i,2) = pressDatas(i)
		end do
		;printVarSummary(newdata)
		;print(newdata(:,0))
		delete(station)
		delete(lons)
		delete(lats)
		delete(pressDatas)
    return newdata
    
end



;******************************************
;writeprocesfilename  将已处理过的文件写入文本，供java驱动跟踪使用，针对数值预报。
;输入：业务ID,B0000，起报时间YYYYMMDDHH，处理文件名，多个文件名以逗号分隔，一次存一行
;输出：处理文件行数。
;******************************************
undef("writeprocesfilename")
procedure writeprocesfilename(businessID,dateTime,fileName)
begin
	  outputPath="/gpfs/jsproduct/NCLDO/"+dateTime
	  checkOrCreateDir(outputPath)
	  outputName=businessID+"_"+dateTime+".do"
	  filen = outputPath+ "/" + outputName
	 
	 if(isfilepresent(filen)) then
  	poem     = asciiread(filen,-1,"string")
  	nlines   = dimsizes(poem)  	
	  fileList = new (nlines+1, "string","xxx")
    fileList(:nlines-1)=poem
    fileList(nlines)=fileName
   else
     fileList = fileName
   end if
   asciiwrite(filen,fileList)
   ;delete(poem)
   delete(outputPath)
   delete(fileList)
end


;******************************************
;deleteolddir  删除历史文件目录。
;输入：rootpath：数据根目录，year,month,day：处理文件的年、月、日，delaySpan：删除多久以前的文件(delaySpan<30)

;******************************************
undef("deleteolddir")
procedure deleteolddir(rootpath,year,month,day,delaySpan)
begin
		   if((stringtoint(day)-delaySpan ).gt. 0) then
		      	oldyear = year
		      	oldmonth=month
		      	oldday = tostring(stringtoint(day)-delaySpan)
		    else
		   	  if(stringtoint(month).gt. 1)then
		        oldyear=year
		        oldmonth=tostring(stringtoint(month)-1)
		        oldday = tostring(days_in_month(stringtoint(year),stringtoint(month)-1)+stringtoint(day)-delaySpan)
		       else
		        oldyear= tostring(stringtoint(year)-1)
		        oldmonth ="12"
		        oldday= tostring(days_in_month(stringtoint(year)-1,12)+stringtoint(day)-delaySpan)
		      end if    
		   end if
		   
		   if(strlen(oldmonth).lt.2) then
		      oldmonth = "0"+oldmonth;
		   end if
		   
		   if(strlen(oldday).lt.2) then
		      oldday = "0"+oldday;
		   end if
   oldpath =rootpath+"/"+oldyear+oldmonth+oldday
   ;print(oldpath)
   delcmd = "rm -rf " + oldpath
   delete(oldyear)
   delete(oldmonth)
   delete(oldday)
   system(delcmd)
   delete(oldpath)
   delete(delcmd)
end

;******************************************
;readprocesfilename  读已处理过的do文件
;输入：业务ID,B0000，起报时间YYYYMMDDHH，处理文件名
;输出：True or False
;******************************************
undef("readprocesfilename")
function readprocesfilename(businessID,dateTime,fileName)
begin
    inputPath="/gpfs/jsproduct/NCLDO/"+dateTime
    inputName=businessID+"_"+dateTime+".do"
    if(.not.isfilepresent(inputPath+"/"+inputName)) then
    		return False
    end if
    fileNames  = asciiread(inputPath+"/"+inputName, -1, "string")
    if(any(fileNames.eq.fileName)) then
        return True
    else
        return False
    end if
end

;******************************************
;获取高度层信息
;******************************************
undef("getHeights")
function getHeights(fl,varName)
begin
	dimName = getfilevardims(fl,varName)
	heights = dimName(0)
	return heights
end