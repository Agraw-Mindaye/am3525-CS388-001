# Lab 2: CodepathMail

Course Link: [CodePath Android Course](https://courses.codepath.org/courses/and102/unit/2#!labs)

Submitted by: **Agraw Mindaye** <!-- Replace 'Your Name Here' with your actual name -->

**CodepathMail** is a simple email app clone inspired by Gmail, allowing users to scroll through a list of emails.

Time spent: **1.5** hours spent in total <!-- Replace 'X' with the number of hours you spent on this project -->

## Application Features

### Required Features

The following **required** functionality is completed:

- [X] (4 pts) **User can scroll through a list of 10 emails.**
    - Each email contains the following information:
        - Sender
        - Email title
        - Email summary
    - <img src= 'emailGif.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

### Stretch Features

The following **stretch** functionality is implemented:

- [X] (3 pts) **User can press a 'Load More' button to see the next 5 emails displayed.**
    - <img src= 'emailGif.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

- [ ] (3 pts) **Each email displays additional information:**
    - Picture of the sender
    - Email sent date
    - Bolded information if the email is unread

## Notes

One challenge I had was implementing the stretch feature of allowing the user to load more emails.
I kept receiving an error for the "email" being immutable, but I was able to fix the issue by 
casting it as a mutable list

## Resources

- [Create dynamic lists with RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
- [Using the RecyclerView](https://guides.codepath.com/android/using-the-recyclerview)

## License

```plaintext
    Copyright [2024] [Codepath]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.