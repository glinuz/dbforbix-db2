# DBforBIX
This is fork from  https://github.com/smartmarmot/DBforBIX. Add IBM DB2 support:JDBC driver, templates, fix-code...<br>

Zabbix server/agent version less than or equal to V3.2  <br>

See Wiki for DBforBix as Zabbix Proxy configuration instructions: https://github.com/smartmarmot/DBforBIX/wiki <br>
DBforBIX is licensed under the GNU General Public License  V.3. <br>


# Directory structure
* dists: packages ready to be used
* items: the itemfiles developed
* zbx_template: zabbix the templeates developed
* conf: a sample configuration
* src: the source code


## Runn

```
java -jar dbforbix_run.jar -b `pwd` -a start
```

