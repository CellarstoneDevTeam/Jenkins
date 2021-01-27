@NonCPS
import groovy.xml.MarkupBuilder 
import groovy.util.*
   
@NonCPS
def getsolutionlist(path) {
//Get Project file from maindirectory
  def findersolution = new FileNameFinder()
  def filessolution = findersolution.getFileNames path, '**/*.csproj'
  list = new ArrayList()
	for (String filesolution : filessolution) {
	    if (filesolution.contains('Test'))
	    {//If project name contains TEST don't compile
        }
         else {
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
def Release = env.Release
def Build = env.Build
def Patch = env.Patch
def Platform = "Any CPU"
def OutputPath = "Obj\\Release"
def ProjectName = solution
            //Take the project name seperately
            ProjectName= ProjectName.substring(ProjectName.lastIndexOf("\\")+1)
            //Replace the project name with blank to get the full path alone
            def ProjectPath = solution.replace(ProjectName,"")

            //copy the build script file to the project path.
            def temp= solution
            def buildscriptPath=""
            temp = temp.split('\\\\');
            buildscriptPath= temp[0] +'\\' + temp[1] + '\\BuildScript'
            print 'buildscript path '+ buildscriptPath


 /*bat '''
        "'''+ MSBuildPath +  '''" ''' + solution  +  ''' /t:Build /p:Configuration=Debug /p:TargetFramework=v4.6.1 /p:version=''' + version */
 print '''
        "'''+ MSBuildPath +  '''" ''' +  buildscriptPath +'\\BuildProject.msbuild'  +  ''' /p:Configuration=Release /p:TargetFramework=v4.6.1 /p:major=''' + MajorVersion + 
        ''' /p:Release=''' + Release   + ''' /p:Build=''' + Build  + ''' /p:Patch=''' +  ''' /p:Platform=''' + Platform + 
        ''' /p:BuildProject=''' + ProjectName +
        ''' /p:ProjectFolder=''' + ProjectPath
	
	bat '''
        "'''+ MSBuildPath +  '''" ''' +  buildscriptPath +'\\BuildProject.msbuild'  +  ''' /p:Configuration=Release /p:TargetFramework=v4.6.1 /p:major=''' + MajorVersion + 
        ''' /p:Release=''' + Release   + ''' /p:Build=''' + Build  + ''' /p:Patch=''' + Patch + ''' /p:Platform="''' + Platform + 
        '''" /p:BuildProject=''' + ProjectName +
        ''' /p:ProjectFolder=''' + ProjectPath +
		  ''' /p:OutputPath=''' + OutputPath
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
def QCEXEDllCopyPath= "${params.CopyPath}"
//def QCVSSProjectFolder = item.lastBuild.getEnvironment(null).get('Project')

def VSSPath = env.VSSPath
def SSDir = env.SSDir

String QCVSSFolder= env.QCVSSFolder

stage(QCVSSProjectFolder + ' Get Source from VSS')
    { 
        bat ''' d:
         cd ''' + QCVSSWorkFolder + '''
        SET SSDIR='''  + SSDir   + '''
        "'''+ VSSPath + ''' CP "''' + QCVSSFolder + '//'+ QCVSSProjectFolder  + '''"''' + '''
        "'''+     VSSPath + ''' Get * -R -W -I-Y" '''
	    
	 
      
     } 
     stage('Compile Project and Copy DLLS')
     {
        //Get project file with full path
        def SolutionList=getsolutionlist(QCVSSWorkFolder + '\\'+ QCVSSProjectFolder)
        
        SolutionList.each 
        {
            def dllname = "$it"
            //Take the project name seperately
            dllname= dllname.substring(dllname.lastIndexOf("\\"))
            //Replace the project name with blank to get the full path alone
            def dllpath = "$it".replace(dllname,"")
             
            //bat "copy " +  rootpath +  "\\BuildProject.msbuild" + " " + dllpath 

            //Copy all dlls from reference folder- This is to resolve any dependancy issues.
            //println "copy " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References " + dllpath +  "\\bin"
	    println " If Exist " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References copy " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References " + dllpath +  "\\bin"	
	    println "solution" + "$it"
                println "before bat"
		println "dllpath=" + dllpath
		//bat " If Exist " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References copy " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\References " + dllpath +  "\\bin"
	 println "after bat"
            compilesolution("$it")
            //Get the output assembly Name it can be exe or dll
            def assembly = getassemblyname("$it")
            println "copy " + dllpath +  "\\obj\\Release\\" + assembly + " " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\" + QCEXEDllCopyPath + " /y"    
	    bat '\"%DigitalSignPath%\\signtool\" sign /f \"%DigitalSignPath%\\mypfxfile.pfx\" /p %DigitalSignPassword% /t http://timestamp.digicert.com/scripts/timstamp.dll \"' + dllpath + "\\obj\\release" +"\\" + assembly   		
            //Copy the assembly file to reference folder - The following projects compiling will refresh the dependancy dlls from this folder
            bat "copy  " + dllpath +  "\\obj\\Release\\" + assembly + " " + QCVSSWorkFolder.replace("\\\\", "\\") + "\\" + QCEXEDllCopyPath + " /y"    
        }
     }
}
            





