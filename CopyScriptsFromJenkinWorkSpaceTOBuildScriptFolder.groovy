
node
{
    
    echo 'test ' + "$WORKSPACE"
    def QCommDBPath=env.QCommDBPath
    def QCVSSWorkFolder= env.QCVSSWorkFolder
     /*bat " If not Exist " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References copy " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References " + dllpath +  "\\bin"*/
      bat " If not Exist " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" + " mkdir \"" + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
     bat "copy \"" + "$WORKSPACE" + '\\..\\GetBuildScriptFromGit\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
     echo "copy \"" + QCommDBPath + '\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" 
    echo "if not exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" + " mkdir " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup"  
    /*bat "if not exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" + " mkdir " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" */
    
    echo "if  exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcomission.bak" + " del " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak"      
    bat "copy \"" + QCommDBPath + '\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup"
    /*bat "ren \"" + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission*.*\" "  + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak"  */
   bat "ren \"" + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission*.*\" qcommission.bak"
    bat "ren \"" + QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\Sample_Service*.*\" qcommission.bak"
    /* echno " ren \"" +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\qcommission\\*.* "  +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\\BuildScript\\\DBBackup\\\qcommission.bak"  */
    /* bat "copy \"" + QCommDBPath + '\\Sample_Service*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\Sample_Service.bak"     */
    
     
}
