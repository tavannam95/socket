export class Menu{
  public static display_menus: any[] = [
    {
      id: 'home',
      name: 'common.menu.home',
      url: '/home',
    },
    {
      id: 'courses',
      name: 'common.menu.courses',
      url: '/courses',
    },
    {
      id: 'guide',
      name: 'common.menu.guide',
      url: '/guide',
    },
    // {
    //   id: 'instructors',
    //   name: 'common.menu.instructor',
    //   url: '/instructors',
    // },

    {
      id: 'aboutUs',
      name: 'common.menu.aboutUs',
      url: '/about-us',
    },

  ];

  public static menus: any[] = [
    {
      id: 'home',
      name: 'common.menu.home',
      url: '/home',
    },
    {
      id: 'courses',
      name: 'common.menu.courses',
      url: '/courses',
      children: [
        {
          id: "course_detail",
          name: "course.detail",
          url: '/courses/detail',
        },
      ]
    },
    {
      id: 'community',
      name: 'common.menu.community',
      // url: '/community',
      children: [
        {
          id: 'instructors',
          name: 'common.menu.instructor',
          url: '/instructors',
        },
        {
          id: 'student',
          name: 'common.menu.student',
          url: '/students',
        },
        {
          id: 'news',
          name: 'common.menu.news',
          url: '/blog',
        },
        {
          id: "blog_detail",
          name: "blog.detail",
          url: '/blog/detail',
        },
        {
          id: "teacher_detail",
          name: "instructors.info",
          url: '/instructors/detail',
        },
        {
          id: "teacher_detail",
          name: "instructors.info",
          url: '/instructors/detail',
        },
      ]
    },
    {
      id: 'contact',
      name: 'common.menu.contact',
      children: [
        {
          id: 'contact-us',
          name: 'common.menu.contact-us',
          url: '/contact-us',
        },
        {
          id: 'apply',
          name: 'common.menu.apply',
          url: '/apply',
        },

      ]
    },
    {
      id: 'guide',
      name: 'common.menu.guide',
      url: '/guide',
    },
    {
      id: 'aboutUs',
      name: 'common.menu.aboutUs',
      url: '/about-us',
    },

  ]
}
