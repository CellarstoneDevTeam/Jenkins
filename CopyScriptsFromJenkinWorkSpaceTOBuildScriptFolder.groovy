
node
{
    
    echo 'test ' + "$WORKSPACE"
    def QCommDBPath=env.QCommDBPath
    def QCVSSWorkFolder= env.QCVSSWorkFolder
    
     bat "copy \"" + "$WORKSPACE" + '\\..\\GetBuildScriptFromGit\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript"
     echo "copy \"" + QCommDBPath + '\\*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" 
    echo "copy \"" + QCommDBPath + '\\*.*\" ' +  "d:\\qcommdb.bak"      
     bat "copy \"" + QCommDBPath + '\\Qcommission*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\qcommission.bak" 
     bat "copy \"" + QCommDBPath + '\\Sample_Service*.*\" ' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript\\Sample_Service.bak"      
    
     
}
