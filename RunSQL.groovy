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

          

          def QCVSSWorkFolder = env.QCVSSWorkFolder

          bat '''

          "'''+ MSBuildPath +  '''" ''' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" +'\\BuildWeb.msbuild'  +  ''' /t:''' + Target + 

          ''' /p:FTPHOST="''' + FTPHost +

          ''' /p:FTPUserName="''' + FTPUserName +

          ''' /p:FTPPassword="''' + FTPPassword +

          '''" /p:FTPFolder="''' + FTPFolder + '''"'''

    }

}