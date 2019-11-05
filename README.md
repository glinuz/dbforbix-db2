# DBforBIX
This is fork from  https://github.com/smartmarmot/DBforBIX. Add IBM DB2 support:JDBC driver, templates, fix-code...<br>

Zabbix server/agent version less than or equal to V3.2  <br>

See Wiki for DBforBix as Zabbix Proxy configuration instructions: https://github.com/smartmarmot/DBforBIX/wiki <br>
DBforBIX is licensed under the GNU General Public License  V.3. <br>


## Directory structure
* dists: packages ready to be used
* items: the itemfiles developed
* zbx_template: zabbix the templeates developed
* conf: a sample configuration
* src: the source code

## Configure
1 DB2 server must open monitor

```
db2 update monitor switches using bufferpool on
db2 update dbm cfg using DFT_MON_BUFPOOL on
```
2 config file like
```
###DB2 Server
DB.SAMPLE.Type=DB2
DB.SAMPLE.Instance=sample  #database name
DB.SAMPLE.Url=jdbc:db2://loclahost:60000/sample
DB.SAMPLE.User=db2inst1
DB.SAMPLE.Password=db2inst1
DB.SAMPLE.MaxActive=10
DB.SAMPLE.MaxWaitMillis=10000
DB.SAMPLE.QueryTimeout=15
DB.SAMPLE.MaxIdle=1
```
## Runn

```
java -jar dbforbix_run.jar -b `pwd` -a start
```

