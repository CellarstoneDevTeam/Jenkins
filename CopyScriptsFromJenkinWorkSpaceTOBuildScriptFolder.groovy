
node
{
    
    echo 'test ' + "$WORKSPACE"
    def QCommDBPath=env.QCommDBPath
    def QCVSSWorkFolder= env.QCVSSWorkFolder
    
     bat "copy \"" + "$WORKSPACE" + '\\..\\GetBuildScriptFromGit\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
     echo "copy \"" + QCommDBPath + '\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" 
    echo "if not exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" + " mkdir " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup"  
    /*bat "if not exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" + " mkdir " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" */
    
    echo "if  exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcomission.bak" + " del " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak"      
    bat "copy \"" + QCommDBPath + '\\Qcommission\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
    echo "ren \"" + QCVSSWorkFolder.replace("\\\\", "\\") +""
    echo "\\BuildScript\\DBBackup\\qcommission*.* "  + ""
    echo QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak"  
   /* echno " ren \"" +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\qcommission\\*.* "  +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak"  */
     bat "copy \"" + QCommDBPath + '\\Sample_Service*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\Sample_Service.bak"      */
    
     
}
