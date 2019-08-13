
node
{
    
    echo 'test ' + "$WORKSPACE"
    def QCommDBPath=env.QCommDBPath
    def QCVSSWorkFolder= env.QCVSSWorkFolder
    
     bat "copy \"" + "$WORKSPACE" + '\\..\\GetBuildScriptFromGit\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
     echo "copy \"" + QCommDBPath + '\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" 
     bat "if not exist " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" + " mkdir " +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup" 
     bat "copy \"" + QCommDBPath + '\\Qcommission*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\qcommission.bak" 
     bat "copy \"" + QCommDBPath + '\\Sample_Service*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\DBBackup\\Sample_Service.bak"      
    
     
}
