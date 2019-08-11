node

{

    def Target= "${params.Target}"

    stage('Restore QCommDB')

    { 

    

        

          def MSBuildPath = env.MSBuildPath

          def FTPHOST = env.FTPHOST

          def FTPUserName = env.FTPUserName

          def FTPPassword = env.FTPPassword

          def FTPFolder = env.FTPFolder
          def SQLDatasource = env.SQLDatasource
          def SQLUserName = env.SQLUserName
          def SQLPassword = env.SQLPassword
          

          def QCVSSWorkFolder = env.QCVSSWorkFolder

          bat '''

          "'''+ MSBuildPath +  '''" ''' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" +'\\BuildWeb.msbuild'  +  ''' /t:''' + Target + 
          ''' /p:SQLDatasource=''' + SQLDatasource +
          ''' /p:SQLUserName=''' + SQLUserName +     
          ''' /p:SQLPassword=''' + SQLPassword +              
          ''' /p:FTPHOST="''' + FTPHost +

          ''' /p:FTPUserName="''' + FTPUserName +

          ''' /p:FTPPassword="''' + FTPPassword +

          '''" /p:FTPFolder="''' + FTPFolder + '''"'''

    }

}
