﻿namespace StudMap.Core
{
    public enum ResponseError
    {
        None = 0,

        #region Register
        UserNameDuplicate = 101,
        UserNameInvalid = 102,
        PasswordInvalid = 103,
        #endregion

        #region Login
        LoginInvalid = 110,
        #endregion
    }
}