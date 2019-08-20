node

{

    def Target= "${params.Target}"
    def SQLDB= "${params.SQLDB}"
    
    def QueryFile ="ValidationQuery.sql"
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
          "'''+ MSBuildPath +  '''" ''' +  QCVSSWorkFolder.replace("\\\\", "\\") + "\\BuildScript" +'\\RunSQL.msbuild'  +  ''' /t:''' + Target + 
          ''' /p:QueryFile=''' + QueryFile +
          ''' /p:SQLDatasource=''' + SQLDatasource +
          ''' /p:SQLUserName=''' + SQLUserName +     
          ''' /p:SQLPassword=''' + SQLPassword +              
          ''' /p:SQLDB=''' + SQLDB +  
          ''' /p:FTPHOST=''' + FTPHost +
          ''' /p:FTPUserName=''' + FTPUserName +
          ''' /p:FTPPassword=''' + FTPPassword +
          ''' /p:FTPFolder="''' + FTPFolder + '''"'''

    }

}
