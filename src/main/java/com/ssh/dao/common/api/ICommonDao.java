package com.ssh.dao.common.api;

import org.hibernate.Session;

public interface ICommonDao {
    /**
     * 获取session
     * @return
     */
    Session getSession();
}
