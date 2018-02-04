from sqlalchemy import String, Column, Table, Integer, ForeignKey, create_engine, DateTime, Boolean, Float, Text
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, sessionmaker
import datetime
from sqlalchemy.sql import select
Base = declarative_base()
Event_has_Tags = Table('event_has_tags', Base.metadata,
                       Column("event_id", Integer, ForeignKey("event.id")),
                       Column("tag_id", Integer, ForeignKey("tag.id")))
Task_has_Tags = Table('task_has_tags', Base.metadata,
                      Column("task_id", Integer, ForeignKey("task.id")),
                      Column("tag_id", Integer, ForeignKey("tag.id")))


class Event(Base):
    __tablename__ = "event"
    attrs = ["title", "id", "begin_date", "end_date", "all_day", "event_color",
             "description", "busy_level", "contact", "location", "str_tags"]
    id = Column(Integer, primary_key=True, nullable=False)
    title = Column(String(100), nullable=False)
    begin_date = Column(DateTime, nullable=False)
    end_date = Column(DateTime, nullable=False)
    all_day = Column(Boolean, nullable=False)
    event_color = Column(String(6), nullable=False)
    description = Column(Text)
    busy_level = Column(Integer)
    contact = Column(String(50))
    location = Column(ForeignKey("location.id"))
    tags = relationship("Tag", secondary=Event_has_Tags, backref="event")

    def _find_or_create_tag(self, tag):
        q = Tag.query.filter_by(name=tag)
        t = q.first()
        if not(t):
            t = Tag(tag)
        return t

    def _get_tags(self):
        return [x.name for x in self.tags]

    def _set_tags(self, value):
        # clear the list first
        while self.tags:
            del self.tags[0]
        # add new tags
        for tag in value:
            self.tags.append(self._find_or_create_tag(tag))

    str_tags = property(_get_tags,
                        _set_tags,
                        "Property str_tags is a simple wrapper for tags relation")


class Tag(Base):
    attrs = ["title", "color"]
    __tablename__ = "tag"
    id = Column(Integer, primary_key=True, nullable=False)
    title = Column(String(100), nullable=False)
    color = Column(String(6))


class Location(Base):
    __tablename__ = "location"
    id = Column(Integer, primary_key=True, nullable=False)
    title = Column(String(60), nullable=False)
    address = Column(String(80), nullable=False)
    lat = Column(Float, nullable=False)
    lng = Column(Float, nullable=False)
    data_type = Column(String(30), nullable=False)


class Task(Base):
    attrs = ["title", "due_date", "completed",
             "priority", "description", "color", "tags"]
    __tablename__ = "task"
    id = Column(Integer, primary_key=True, nullable=False)
    title = Column(String(100), nullable=False)
    due_date = Column(DateTime)
    completed = Column(Boolean, nullable=False)
    priority = Column(Integer, nullable=False)
    description = Column(Text)
    color = Column(String(20))
    tags = relationship("Tag", secondary=Task_has_Tags, backref="task")
    parent_task_list = Column(Integer, ForeignKey("tasklist.id"), nullable=False)

    def _find_or_create_tag(self, tag):
        # print(tag["title"])
        q = Session.query(Tag).filter_by(title=tag["title"])
        t = q.first()
        if not(t):
            t = Tag(title=tag, color=tag["color"])
        return t

    def _get_tags(self):
        return [x.name for x in self.tags]

    def _set_tags(self, value):
        if not value:
            return
        # clear the list first
        while self.tags:
            del self.tags[0]
        # add new tags

        for tag in value:
            self.tags.append(self._find_or_create_tag(tag))

    str_tags = property(_get_tags,
                        _set_tags,
                        "Property str_tags is a simple wrapper for tags relation")

class SubTask(Base):
    __tablename__ = "subtask"
    title = Column(String(100), primary_key=True, nullable=False)
    parent_task = Column(Integer, ForeignKey("task.id"))

class TaskList(Base):
    __tablename__ = "tasklist"
    id = Column(Integer, primary_key=True, nullable=False)
    title = Column(String(100),nullable=False)
    repeatable = Column(Boolean)



# create the connection and session
engine = create_engine(
    "mysql+mysqldb://volerous:fourarms@localhost/Personal_Assistant")
#engine.execute("USE Personal_Assistant")
Base.metadata.create_all(engine)
session_m = sessionmaker(bind=engine)
Session = session_m()
