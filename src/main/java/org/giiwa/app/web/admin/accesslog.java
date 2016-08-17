/*
 * Copyright 2015 Giiwa, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.giiwa.app.web.admin;

import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.bean.AccessLog;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

/**
 * web api: /admin/accesslog <br>
 * used to access the "accesslog"
 * 
 * @author joe
 *
 */
public class accesslog extends Model {

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.framework.web.Model#onGet()
   */
  @Path(login = true, access = "acess.logs.admin")
  public void onGet() {
    String uri = this.getString("guri");
    String ip = this.getString("ip");
    String gsid = this.getString("gsid");
    String sortby = this.getString("sortby");
    int sortby_type = this.getInt("sortby_type", -1);

    W q = W.create();
    if (!X.isEmpty(uri)) {
      q.and("url", uri);
      this.set("guri", uri);
    }
    if (!X.isEmpty(ip)) {
      q.and("ip", ip);
      this.set("ip", ip);
    }
    if (!X.isEmpty(gsid)) {
      q.and("sid", gsid);
      this.set("gsid", gsid);
    }
    int s = this.getInt("s");
    int n = this.getInt("n", 10, "number.per.page");

    if (X.isEmpty(sortby)) {
      sortby = "created";
    }
    this.set("sortby", sortby);
    this.set("sortby_type", sortby_type);

    q.sort(sortby, sortby_type);
    Beans<AccessLog> bs = AccessLog.load(q, s, n);

    this.set(bs, s, n);

    this.query.path("/admin/accesslog");
    this.show("/admin/accesslog.index.html");
  }

  /**
   * Deleteall.
   */
  @Path(path = "deleteall", login = true, access = "acess.logs.admin")
  public void deleteall() {
    AccessLog.deleteAll();
  }

}
