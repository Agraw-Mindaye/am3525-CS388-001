# Lab 3: NY Times Bestselling Books

Course Link: [CodePath Android Course](https://courses.codepath.org/courses/and102/unit/3#!labs)

Submitted by: **Agraw Mindaye** <!-- Replace 'Your Name Here' with your actual name -->

**NY Times Bestselling Books** is an app designed to display the current bestselling books based on NY Times data.

Time spent: **4** hours spent in total <!-- Replace 'X' with the number of hours you spent on this project -->

## Application Features

### Required Features

The following **required** functionality is completed:

- [x] (2 pts) **Live data is loaded from the NY Times API.**
    - <img src='NYTimesBooks.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' /> <!-- Replace this link with your actual image/GIF link -->
- [x] (4 pts) **Books are displayed using a RecyclerView.**
    - Displays book ranking, cover, title, author, and description.
    - Book cover images are downloaded using Glide.
  - <img src='NYTimesBooks.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' /> <!-- Replace this link with your actual image/GIF link -->

### Stretch Features

The following **stretch** functionality is implemented:

- [ ] (4 pts) **Improved layout and styling to match the NY Times website.**
    - Includes a "buy" button that links to Amazon.
    - ![Image/GIF showing stretch features](http://i.imgur.com/link/to/your/gif/file.gif) <!-- Replace this link with your actual image/GIF link -->

## Notes

The hardest part of this Lab was figuring out how to 
parse the JSON files to retrieve the required fields

## Resources

- [CodePath's AsyncHTTPClient library](https://guides.codepath.org/android/Using-CodePath-Async-Http-Client)
- [Displaying Images with Glide library](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library)
- [Parsing JSON responses to Models](https://guides.codepath.org/android/converting-json-to-models)
- [Parsing JSON with gson library](https://guides.codepath.org/android/Leveraging-the-Gson-Library#parsing-the-response)
- [Kotlin's Guide on Serialization](https://kotlinlang.org/docs/serialization.html)

## License

```plaintext
    Copyright [2024] [CodePath]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.