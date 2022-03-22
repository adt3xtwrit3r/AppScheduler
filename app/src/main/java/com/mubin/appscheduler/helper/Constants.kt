package com.mubin.appscheduler.helper

object Constants {

    const val BASE_URL = "https://api.tvmaze.com/"
    const val BASE_URL2 = "https://www.episodate.com/"
    const val END_POINT = "shows"
    const val END_POINT2 = "api/most-popular"

    //Flags
    const val FLAG_UPDATE = "FLAG_UPDATE"
    const val FLAG_NEW = "FLAG_NEW"

    //Default Image Placeholder
    const val placeholder64 = "iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAYAAADimHc4AAAABmJLR0QA/wD/AP+gvaeTAAACRElEQVR4nO3csU4UURyF8XNnNlrIUvICLi9iDCFBSyopDLXhAYyYLXwCsCM8gIWJCQqdj2AlMVF8Am1YYmIhey02sSAzMDPs3TPXfL/6LjnkUyr4SwAAwCLc+GK8tqKy3FEIG5JWJd1LvOmXpK+SPqiIe3p+8uNWX63n+68P8GpjU4qHkobz29fKRNK2Xhy/7fTpDPbXB5iNf3Ptm8WIkjZbR8hkf/W48dqKBoMz+f7lXDVREUeNfxxltL+ofF6WO+rPeEla1lTPGr/OaH91gBAeJZ3TSXjc/Gk++6sDKNxPOaWjUfOn+eyvCRCXUi7pqMWPlHz21wTAohDAjABmBDAjgBkBzAhgRgAzApgRwIwAZgQwI4AZAcwIYEYAMwKYEcCMAGYEMCOAGQHMCGBWF+BioSuambR4m83+ugDfEw7pKJ61eJzN/uoAMRwl3dJFm00Z7a/57ejpvtr9l0/tXJfl68avM9pfHWD2e+zbmv1xgdtUMT7V+Ohn409ktL+s/djHb1/0YPWzpHVJdxONu8m5VDzR7vG71p/MZH99AGn2TTwcHWpa/FaIQ6kYSroz75VXXEjxVNKB/gy29PL9p85fKff9AAAAAAD8d7gXNH/cC/ong/3cC0qPe0E9wL0gM+4F+XEvyI17QWbcC+ojApgRwIwAZgQwI4AZAcwIYEYAMwKYEcCMAGYEMCOAGQHMCGBGADMCmBHAjABmBDAjgBkBzLgXtDjcC/LiXpAX94KsuBdkxL0g9Xg/94Lmj3tBAADk4C+KLCJwtUyEEgAAAABJRU5ErkJggg=="

    val <T> T.exhaustive: T
        get() = this

}