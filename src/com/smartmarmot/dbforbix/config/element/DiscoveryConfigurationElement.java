/*
 * This file is part of DBforBix.
 *
 * DBforBix is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DBforBix is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DBforBix. If not, see <http://www.gnu.org/licenses/>.
 */

package com.smartmarmot.dbforbix.config.element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.smartmarmot.dbforbix.zabbix.ZabbixItem;

public class DiscoveryConfigurationElement extends AbstractConfigurationElement {
	
	private static final Logger		LOG				= Logger.getLogger(DiscoveryConfigurationElement.class);
	private String		discoveryItemKey;
	private String[]	altNames;

	public DiscoveryConfigurationElement(String _prefix, int time, String _item, String _names, String _query) {		
		super(_prefix, time, _item, "", _query);
		discoveryItemKey = _prefix+_item;
		altNames = _names.split("\\|");		
	}
	
	@Override
	public ZabbixItem[] getZabbixItemsData(Connection con, int timeout) throws SQLException {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		Long clock = new Long(System.currentTimeMillis() / 1000L);

		try(PreparedStatement pstmt = con.prepareStatement(getQuery())){
			pstmt.setQueryTimeout(timeout);
			try(ResultSet rs = pstmt.executeQuery()){
				ResultSetMetaData meta = rs.getMetaData();
		
				builder.append("{\"data\":[");
				while (rs.next()) {
					if (first)
						builder.append("{");
					else
						builder.append(",{");
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						
						/**
						 * {"request":"agent data","data":[
						 * 		{"host":"zabdomis01","key":"net.if.discovery","value":"{\"data\":[{\"{#IFNAME}\":\"lo\"},{\"{#IFNAME}\":\"eno16780032\"}]}","clock":1481573917,"ns":417446137},
						 * 		{"host":"zabdomis01","key":"vfs.fs.discovery","value":"{\"data\":[
						 * 			{\"{#FSNAME}\":\"/\",\"{#FSTYPE}\":\"rootfs\"},{\"{#FSNAME}\":\"/sys\",\"{#FSTYPE}\":\"sysfs\"},{\"{#FSNAME}\":\"/proc\",\"{#FSTYPE}\":\"proc\"},{\"{#FSNAME}\":\"/dev\",\"{#FSTYPE}\":\"devtmpfs\"},{\"{#FSNAME}\":\"/sys/kernel/security\",\"{#FSTYPE}\":\"securityfs\"},				 
						 * 			{\"{#FSNAME}\":\"/dev/shm\",\"{#FSTYPE}\":\"tmpfs\"},{\"{#FSNAME}\":\"/dev/pts\",\"{#FSTYPE}\":\"devpts\"},{\"{#FSNAME}\":\"/run\",\"{#FSTYPE}\":\"tmpfs\"},{\"{#FSNAME}\":\"/sys/fs/cgroup\",\"{#FSTYPE}\":\"tmpfs\"},
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/systemd\",\"{#FSTYPE}\":\"cgroup\"},{\"{#FSNAME}\":\"/sys/fs/pstore\",\"{#FSTYPE}\":\"pstore\"},				 
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/cpu,cpuacct\",\"{#FSTYPE}\":\"cgroup\"},{\"{#FSNAME}\":\"/sys/fs/cgroup/devices\",\"{#FSTYPE}\":\"cgroup\"},
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/perf_event\",\"{#FSTYPE}\":\"cgroup\"},{\"{#FSNAME}\":\"/sys/fs/cgroup/hugetlb\",\"{#FSTYPE}\":\"cgroup\"},				 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/cpuset\",\"{#FSTYPE}\":\"cgroup\"},{\"{#FSNAME}\":\"/sys/fs/cgroup/net_cls\",\"{#FSTYPE}\":\"cgroup\"},
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/memory\",\"{#FSTYPE}\":\"cgroup\"},				 
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/freezer\",\"{#FSTYPE}\":\"cgroup\"},				 
						 * 			{\"{#FSNAME}\":\"/sys/fs/cgroup/blkio\",\"{#FSTYPE}\":\"cgroup\"},				 
						 * 			{\"{#FSNAME}\":\"/sys/kernel/config\",\"{#FSTYPE}\":\"configfs\"},
						 * 			{\"{#FSNAME}\":\"/\",\"{#FSTYPE}\":\"xfs\"},				 
						 * 			{\"{#FSNAME}\":\"/proc/sys/fs/binfmt_misc\",\"{#FSTYPE}\":\"autofs\"},				 * 			{\"{#FSNAME}\":\"/dev/hugepages\",\"{#FSTYPE}\":\"hugetlbfs\"},				 * 			{\"{#FSNAME}\":\"/sys/kernel/debug\",\"{#FSTYPE}\":\"debugfs\"},
						 * 			{\"{#FSNAME}\":\"/dev/mqueue\",\"{#FSTYPE}\":\"mqueue\"},				 * 			{\"{#FSNAME}\":\"/proc/sys/fs/binfmt_misc\",\"{#FSTYPE}\":\"binfmt_misc\"},
						 * 			{\"{#FSNAME}\":\"/proc/fs/nfsd\",\"{#FSTYPE}\":\"nfsd\"},				 * 			{\"{#FSNAME}\":\"/var\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/var/lib/nfs/rpc_pipefs\",\"{#FSTYPE}\":\"rpc_pipefs\"},{\"{#FSNAME}\":\"/home\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/opt\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/tmp\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/boot\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/app\",\"{#FSTYPE}\":\"xfs\"},{\"{#FSNAME}\":\"/misc\",\"{#FSTYPE}\":\"autofs\"},{\"{#FSNAME}\":\"/net\",\"{#FSTYPE}\":\"autofs\"},{\"{#FSNAME}\":\"/run/user/354789\",\"{#FSTYPE}\":\"tmpfs\"},{\"{#FSNAME}\":\"/run/user/0\",\"{#FSTYPE}\":\"tmpfs\"},
						 * 			{\"{#FSNAME}\":\"/run/user/59425\",\"{#FSTYPE}\":\"tmpfs\"},
						 * 			{\"{#FSNAME}\":\"/run/user/361033\",\"{#FSTYPE}\":\"tmpfs\"}]
						 * 		}",
						 * 		"clock":1481573917,"ns":417779254}],
						 * "clock":1481573922,"ns":418957607}
						 */
						
						/**
						 * comma for separate names inside one discovery block
						 */
						if(i>1) builder.append(",");
						if (altNames == null)
							builder.append("\"{#" + meta.getColumnName(i).toUpperCase() + "}\":\"" + rs.getString(i).replace("\\", "\\\\") +							
									"\"");
						else
							builder.append("\"{#" + altNames[i-1].toUpperCase() + "}\":\"" + rs.getString(i).replace("\\", "\\\\") +							
									"\"");
					}
					/**
					 * close discovery block
					 */
					builder.append("}");
					/**
					 * first discovery block has been passed
					 */
					first = false;
				}
			}catch(SQLException ex){
				throw ex;
			}
		}catch(SQLException ex){
			LOG.error("Cannot get data for item:\n" + getElementID() +"\nQuery:\n"+getQuery(), ex);
			throw ex;
		}

		builder.append("]}");
		return new ZabbixItem[] { new ZabbixItem(discoveryItemKey, builder.toString(),ZabbixItem.ZBX_STATE_NORMAL,clock, this) };
	}

}
