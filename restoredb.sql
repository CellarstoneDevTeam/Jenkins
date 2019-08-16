Declare @SQL_SCRIPT varchar(max)
Declare @MDFFileLocicalName varchar(max)
Declare @LDFFileLogicalName varchar(max)

Declare @MDFFileName varchar(max)
Declare @LDFFileName varchar(max)
Declare @BackupFileName varchar(max)
return
set @MDFFileLocicalName =  @SQLDB + 'MDF'
set @LDFFileLogicalName =  @SQLDB + 'LDF'

set @MDFFileName =  @SQLDB + '.MDF'
set @LDFFileName =  @SQLDB + '.LDF'

set @BackupFileName = SQLDB + '.bak'

IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'@SQLDB')
BEGIN
    
   CREATE DATABASE @SQLDB
   ON
   ( NAME = @MDFFileLocicalName,  
       FILENAME = @MDFFileName ,
       SIZE = 10,
       MAXSIZE = 50,
       FILEGROWTH = 5 )  
   LOG ON
   ( NAME = @LDFFileLogicalName,  
       FILENAME = @LDFFileName,
       SIZE = 5,
       MAXSIZE = 25,
       FILEGROWTH = 5 )
   
END
ALTER DATABASE @SQLDB
SET SINGLE_USER WITH
ROLLBACK IMMEDIATE
--$(FTPPath)
----Restore Database
--RESTORE DATABASE QCBuild FROM DISK = @BackupFile WITH replace 
SET @SQL_SCRIPT = 'RESTORE DATABASE QCommission FROM DISK = '''+ @FTPPath + '''\''' + BackupFileName + ' WITH REPLACE'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + @FTPPath + '\Qcommission.bak'' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + ''' WITH replace;'
--SET @SQL_SCRIPT = 'RESTORE DATABASE QCBuild FROM DISK = ''' + QUOTENAME(@FTPPath + '\Qcommission.bak') +  'WITH replace;'
EXECUTE (@SQL_SCRIPT)
/*use qcbuild
delete from buildlog
insert into buildlog (logtext) values (@SQL_SCRIPT)*/

 
/*If there is no error in statement before database will be in multiuser
mode.
If error occurs please execute following command it will convert
database in multi user.*/
ALTER DATABASE @SQLDB SET MULTI_USER
GO

