# DBforBIX


This is fork from  https://github.com/smartmarmot/DBforBIX. Add IBM DB2 support:JDBC driver, templates, fix-code...


See Wiki for DBforBix as Zabbix Proxy configuration instructions: https://github.com/smartmarmot/DBforBIX/wiki<br>

DBforBIX is licensed under the GNU General Public License  V.3. <br>
You can obtain a full copy of the licese here: https://www.gnu.org/licenses/gpl-3.0.txt <br>


# Directory structure
* dists: contains the packages ready to be used
* items: contains all the itemfiles developed (also included in the distribution package)
* zbx_template: contains all the templeates developed (also included in the distribution package)
* conf: contains a sample configuration (also included in the distribution package)
* src: contains all the source code


## Runn

```
java -jar dbforbix_run.jar -b `pwd` -a start
```

