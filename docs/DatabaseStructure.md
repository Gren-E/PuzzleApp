# Database Structure
<br> 


`pictures` 

Table represents all pictures known to the application, that can be used for game creation. Source file is a relative path based on the picture catalog.

| **id** | source_file | upload_timestamp    |
|--------|-------------|---------------------|
| 1      | image.jpg   | 2026-10-13 14:00:34 |
| 2      | image2.jpg  | 2026-10-15 06:30:15 |

<br>

`picture_tags`

Table lists all tags related to a specific picture, allowing the picture catalog to be filtered by keywords.

| **picture_id** | tag    |
|----------------|--------|
| 1              | nature |
| 1              | winter |
| 2              | winter |

<br>

`game_templates`

Table represents the most common game settings templates and their exact specifications, for easier game creation.

| **id** | picture_id | piece_style | rows | columns | rotation |
|--------|------------|-------------|------|---------|----------|
| 1      | 1          | CLASSIC     | 10   | 14      | true     |              
| 2      | 2          | TRAPEZOID   | 15   | 28      | false    |

<br>

`games`

Table keeps track of all previously solved puzzles and current games in progress.

| **id** | status      | template_id | picture_id | piece_style | rows | columns | rotation | start_timestamp     | duration_in_seconds |
|--------|-------------|-------------|------------|-------------|------|---------|----------|---------------------|---------------------|
| 1      | IN_PROGRESS | 2           | 1          | CLASSIC     | 10   | 14      | true     | 2026-10-25 13:23:00 | 234455              |
| 2      | FINISHED    | *null*      | 2          | TRAPEZOID   | 15   | 28      | false    | 2026-10-28 11:10:05 | 2345255             |

<br>

`puzzle_data`

Table describes the current state of a game, keeps track of each piece and its position, allowing games to be played, saved and resumed at any time.

| **game_id** | piece_ordinal | cluster_id | piece_x | piece_y | orientation | neighbouring_ordinals | shape  |
|-------------|---------------|------------|---------|---------|-------------|-----------------------|--------|
| 1           | 1             | 1          | 20      | 50      | 90          | null, null, 11, 2     | *BLOB* |
| 1           | 2             | 1          | 20      | 70      | 90          | null, 1, 12, 3        | *BLOB* |
| 2           | 6             | -1         | 50      | 0       | 0           | null, 5, 21, 7        | *BLOB* |

