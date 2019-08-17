Declare @SQL_SCRIPT varchar(max)
declare @sqlstr varchar(max)
declare @BackupFileName varchar(max)
DECLARE @file_name_d nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultDataPath'))
DECLARE @file_name_l nvarchar(200) = CONVERT(sysname, SERVERPROPERTY('InstanceDefaultLogPath'))

set @BackupFileName =   '''+ @SQLDB + ''' + ''' + .bak'''

--IF  NOT EXISTS (SELECT '1' FROM sys.databases WHERE name = ''' +@SQLDB + '''')
--BEGIN
SET @sqlstr =  'IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name =''' +@SQLDB+ ''''
      SET @sqlstr =  @sqlstr + ' CREATE DATABASE'+' '+@SQLDB+' '
        SET @sqlstr =  @sqlstr +'ON'
        SET @sqlstr =  @sqlstr +'('    
        SET @sqlstr =  @sqlstr +'NAME = '+' '+@SQLDB+'_dat,'
        SET @sqlstr =  @sqlstr +'FILENAME = '+''''+@file_name_d+''+@SQLDB+'.mdf'','
        SET @sqlstr =  @sqlstr +'SIZE = 10,'
        SET @sqlstr =  @sqlstr +'MAXSIZE = 50,'
        SET @sqlstr =  @sqlstr +'FILEGROWTH = 5' 
        SET @sqlstr =  @sqlstr +')'
        SET @sqlstr =  @sqlstr +'LOG ON'
        SET @sqlstr =  @sqlstr +'(   NAME = '+' '+@SQLDB+'_log,'
        SET @sqlstr =  @sqlstr +'FILENAME = '+''''+@file_name_l+''+@SQLDB+'.ldf'','
        SET @sqlstr =  @sqlstr +'SIZE = 5MB,'
        SET @sqlstr =  @sqlstr +'MAXSIZE = 25MB,'
        SET @sqlstr =  @sqlstr +'FILEGROWTH = 5MB'
        SET @sqlstr =  @sqlstr +');'
       
        Print (@sqlstr) -- Print first if you want to see the output
        --EXEC (@sqlstr);
        Print 'Datbaase '+@SQLDB +' has been created using default data and log location in the server configuration!!'
        Print 'Data file location = '+@file_name_d+@SQLDB+'.mdf';
        Print 'Log file location = '+@file_name_l+@SQLDB+'.ldf';
  
--END
/*set @sqlstr = 'ALTER DATABASE '+ @SQLDB+' '
SET @sqlstr =  @sqlstr +'SET SINGLE_USER WITH '
SET @sqlstr =  @sqlstr +'ROLLBACK IMMEDIATE'
--EXEC (@sqlstr);
Print (@sqlstr);                                                                     
--$(FTPPath)
----Restore Database
--RESTORE DATABASE QCBuild FROM DISK = @BackupFile WITH replace 
SET @SQL_SCRIPT = 'RESTORE DATABASE ' + @SQLDB + ' FROM DISK = '''+ @FTPPath + '\\' + @BackupFileName + ''' WITH REPLACE'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + @FTPPath + '\Qcommission.bak'' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + ''' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + QUOTENAME(@FTPPath + '\Qcommission.bak') +  'WITH replace;'
--EXECUTE (@SQL_SCRIPT)
print (@SQL_SCRIPT)
/*use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)*/

 
/*If there is no error in statement before database will be in multiuser
mode.
If error occurs please execute following command it will convert
database in multi user.*/
--set @sqlstr = 'ALTER DATABASE ' + @SQLDB  + ' SET MULTI_USER'
--EXEC (@sqlstr);                                                                   
print (@sqlstr); */                                                                   
GO



