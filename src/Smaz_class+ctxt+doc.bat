:KODOVANI 852 !!!
:Priserne zlutoucky kun upel dabelske ody
@echo off
echo ###########################################################################
echo ###                                                                     ###
echo ###        Opravdu smazat vsechny *.class a *.ctxt soubory?             ###
echo ###                                                                     ###
echo ###########################################################################
echo 
echo 
echo on
pause

@prompt $g$s
del /s *.class
del /s *.ctxt

@echo off
echo 
echo Chystam se mazat slozky s dokumentaci
for /R /D %%i in (doc) do (
    if EXIST %%i (
        rd /S /Q "%%i"
        @echo SMAZANO: %%i
REM Kdybych chtel pri neuspechu jeste neco delat, vlozim sem:    
REM     ) else (
REM     nasledovane definici akce, ktera by se mela provadet
    )
)

@echo off
echo 
echo 
echo ###########################################################################
echo ###                                                                     ###
echo ###                               KONEC
echo ###                                                                     ###
echo ###########################################################################
echo 
echo 
pause 
