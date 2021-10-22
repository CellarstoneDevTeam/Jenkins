@NonCPS
import groovy.xml.MarkupBuilder 
import groovy.util.*
   
@NonCPS
def getsolutionlist(path) {
//Get Project file from maindirectory
  def findersolution = new FileNameFinder()
  def filessolution = findersolution.getFileNames path, '**/*.sln'
  list = new ArrayList()
	for (String filesolution : filessolution) {
	    if (filesolution.contains('Test')  || filesolution.contains('MaxBlox') || filesolution.contains('sln (') || filesolution.contains('DBST1'))
	    {//If project name contains TEST don't compile
        }
         else {
		 
		 print "file solution" + filesolution	
	 	 list.add(filesolution)}
	}
	File fileDir=new File(path)
    return list
}

@NonCPS
def getdlllist(path) {
//Get dll files from maindirectory
//Currently not using this
  def finderdll = new FileNameFinder()
  def filesdll = finderdll.getFileNames path, '**/*.dll'
  listdll = new ArrayList()
  	for (String filedll : filesdll) {
	 	 listdll.add(filedll)
  	}


//Get dll files from subdirectories
File fileDir=new File(path)
fileDir.eachDirRecurse() 
    {
    dir ->  
    dir.eachFileMatch(~/.*.dll/) { filesolution ->  
         list.add(filesolution)}
    }
return listdll
}

@NonCPS
def compilesolution(solution) {
def MSBuildPath = env.MSBuildPath
def MajorVersion = env.MajorVersion
def FTPHOST = env.FTPHOST
def FTPUserName = env.FTPUserName
def FTPPassword = env.FTPPassword
def Release = env.Release
def Build = env.Build
def Patch = env.Patch
def MinifierPath = env.MinifierPath

	

 def ProjectName = solution
            //Take the project name seperately
            ProjectName= ProjectName.substring(ProjectName.lastIndexOf("\\")+1)
            //Replace the project name with blank to get the full path alone
            def ProjectPath = solution.replace("\\"+ProjectName,"")
	    print "project name2" + ProjectName	
	    print "project path" + ProjectPath	
            //copy the build script file to the project path.
            def temp= solution
            def buildscriptPath=""
            temp = temp.split('\\\\');
            buildscriptPath= temp[0] +'\\' + temp[1] + '\\BuildScript'
            


 /*bat '''
        "'''+ MSBuildPath +  '''" ''' + solution  +  ''' /t:Build /p:Configuration=Release /p:TargetFramework=v4.6.1 /p:version=''' + version */

	if (UploadToFTP=="Yes")
	{
		
		echo 'Target Complete Build with FTP'
		bat '''
        	"'''+ MSBuildPath +  '''" ''' +  buildscriptPath +'\\BuildWeb.msbuild'  +  ''' /t:MinificationProcess /t:CompleteBuild /p:Configuration=Release ''' + 
        	''' /p:BuildProject="''' + ProjectName.replace(".sln","") +
		'''" /p:FTPHOST=''' + FTPHOST +		
		''' /p:FTPUserName=''' + FTPUserName +
		''' /p:FTPPassword=''' + FTPPassword +
        	''' /p:ProjectFolder="''' + ProjectPath + 
		'''" /p:MinifierPath="''' + MinifierPath	+ '''"'''
		 
		
	
	}
	else
	{
		echo 'Target after build only'
		bat '''
		"'''+ MSBuildPath +  '''" ''' +  buildscriptPath +'\\BuildWeb.msbuild'  +  '''  /p:Configuration=Release ''' + 
        	''' /p:BuildProject="''' + ProjectName.replace(".sln","") +
		'''" /p:FTPHOST=''' + FTPHOST +		
		''' /p:FTPUserName=''' + FTPUserName +
		''' /p:FTPPassword=''' + FTPPassword +
        	''' /p:ProjectFolder="''' + ProjectPath + 
		'''" /p:MinifierPath="''' + MinifierPath	+ '''"'''
		
		
	}
}

@NonCPS
def getassemblyname(node)
{
def parser = new XmlParser() 
        
            def doc = parser.parse(node)
            //def people = new XmlSlurper().parseText("$it")
        
            //doc.children().each { println it.name()}
            def assemblyname 
            def ext
            doc.children().each 
            { 
                it.children().each
                {
        
  
                    if (it.toString().contains("AssemblyName"))
            

                    {
                        // bat "copy " + dllpath + it.text() ".dll " + QCVSSWorkFolder + "\\References"    
                       assemblyname= it.text()
                       // break    
                    } 
                    if (it.toString().contains("OutputType"))
            

                    {
                        // bat "copy " + dllpath + it.text() ".dll " + QCVSSWorkFolder + "\\References"    
                      
                      switch(it.text()) {            
                        //There is case statement defined for 4 cases 
                        // Each case statement section has a break condition to exit the loop 
			
                        case "Library": 
                             ext= "dll"
                            break; 
                        case "WinExe": 
                           ext= "exe"
                      
                      }
                       

                       // break    
                    }

           
                }
            }
return assemblyname + "." + ext
}

node
{
	
	

	
def QCVSSWorkFolder=env.QCVSSWorkFolder
//def QCVSSProjectFolder= "${env.JOB_NAME}"

def QCVSSProjectFolder= "${params.Project}"
//def QCVSSProjectFolder = item.lastBuild.getEnvironment(null).get('Project')
def CleanBuild= "${params.CleanBuild}"

def VSSPath = env.VSSPath
def SSDir = env.SSDir

String QCVSSFolder= env.QCVSSFolder
String Precompilefolder =""
	
stage('Get Code from VSS')
    { 
      
	print 'CleanBuild ' + CleanBuild 
	
	 if (CleanBuild =="No")
	    {
		/*Delete only the precompilefolder */
		Precompilefolder = "BuildScript\\PrecompileFolder\\" + QCVSSProjectFolder 
	    }
	  else
	  {
		/*Delete entire source */
		Precompilefolder = "\\" + QCVSSProjectFolder 
	  }
	    
/*	    bat ''' d:
         cd ''' + QCVSSWorkFolder + '''
	 rd /s /q "''' + QCVSSProjectFolder + '''"  */
/*	  bat ''' d:
         cd ''' + QCVSSWorkFolder + ''' 
	 rd /s /q "''' + QCVSSProjectFolder+Precompilefolder + '''"''' + ''' */
	    

 	/* bat '''  cd /d ''' + QCVSSWorkFolder + '''
	 rd /s /q "''' + Precompilefolder + '''"''' + '''
         SET SSDIR='''  + SSDir   + '''
        "'''+ VSSPath + ''' CP "''' + QCVSSFolder + '//'+ QCVSSProjectFolder  + '''"''' +  '''
        "'''+     VSSPath + ''' Get * -R -W -I-Y" '''
	*/
	    
	    bat '''  cd /d ''' + QCVSSWorkFolder + '''
	 rd /s /q "''' + Precompilefolder + '''_Files"'''
	    
	 git credentialsId: 'qc-robot',url: 'http://code.cellarstone.com:3000/QCommission/QCommissionPortal.git'

	  if ( QCVSSProjectFolder== "QCommission Portal SSO")
	    {
		print 'SSO Project skipped copying'
	    }
	    else
	    {
	     
             print 'xcopy \" + ""$WORKSPACE" + '\\' + QCVSSProjectFolder + '\" \"'  +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\QCommissionPortal /E/H/I\"'
	  
	  
	    }
     } 
	
     stage('Compile Project and Copy DLLS')
     {
        //Get project file with full path
        def SolutionList=getsolutionlist(QCVSSWorkFolder + '\\'+ QCVSSProjectFolder )
        
        SolutionList.each 
        {
            def dllname = "$it"
            //Take the project name seperately
            dllname= dllname.substring(dllname.lastIndexOf("\\"))
            //Replace the project name with blank to get the full path alone
            def dllpath = "$it".replace(dllname,"")
		print dllname
            compilesolution("$it")
        }
     }
}
            





