import graphviz
import pathlib
import subprocess


script_dir = pathlib.Path(__file__).parent

dot_dir = script_dir/ 'dot_files'

new_dir = script_dir / 'svg_files'

for file in dot_dir.iterdir():
    if file.suffix == '.dot':
        test = str(new_dir) +file.name[:-4]+'.svg'
        graphviz.render(engine='dot', filepath=file, outfile= str(new_dir)+'/' +file.name[:-4]+'.svg' )
